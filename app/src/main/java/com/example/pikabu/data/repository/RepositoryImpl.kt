package com.example.pikabu.data.repository

import android.annotation.SuppressLint
import com.example.pikabu.data.NoDataException
import com.example.pikabu.data.local.Dao
import com.example.pikabu.data.network.API
import com.example.pikabu.entity.Feed
import com.example.pikabu.entity.Story
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class RepositoryImpl(
    private val api: API,
    private val dao: Dao
) : Repository {

    private val allFeed = arrayListOf<Story>()
    private val savedFeed = arrayListOf<Story>()

    private val allFeedObservable = BehaviorSubject.create<List<Story>>()
    private val savedFeedObservable = BehaviorSubject.create<List<Story>>()

    init {
        getStoriesFromDatabase()
    }

    override fun getFeed(feedType: String): Observable<List<Story>> {
        return when (feedType) {
            Feed.ALL -> allFeedObservable
            Feed.SAVED -> savedFeedObservable
            else -> Observable.just(listOf())
        }
    }

    override fun getStory(storyId: Int): Single<Story> {
        return api.getStory(storyId)
            .map { s ->
                s.saved = savedFeed.find { it.id == storyId }?.saved ?: s.saved
                s
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun changeStoryStatus(storyId: Int, save: Boolean) {
        val notSaved = allFeed.find { it.id == storyId }
        val saved = savedFeed.find { it.id == storyId }
        when {
            save && saved == null && notSaved != null -> {
                savedFeed.add(notSaved)
            }
        }
        allFeed.find { it.id == storyId }?.saved = save
        savedFeed.find { it.id == storyId }?.saved = save
        allFeedObservable.onNext(allFeed)
        savedFeedObservable.onNext(savedFeed)
        saveFeed(savedFeed)
    }

    @SuppressLint("CheckResult")
    private fun getStoriesFromDatabase() {
        dao.getFeed()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    savedFeed.addAll(it)
                    getStoriesFromServer()
                },
                { savedFeedObservable.onError(it) }
            )
    }

    @SuppressLint("CheckResult")
    private fun getStoriesFromServer() {
        api.getFeed()
            .map { list ->
                if (savedFeed.isNotEmpty()) {
                    allFeed.addAll(list)
                    allFeed.forEach { story ->
                        story.saved = savedFeed.find { it.id == story.id }?.saved ?: story.saved
                    }
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    allFeedObservable.onNext(allFeed)
                    if (savedFeed.isEmpty()) savedFeedObservable.onError(NoDataException())
                    else savedFeedObservable.onNext(savedFeed)
                },
                { allFeedObservable.onError(it) }
            )
    }

    private fun saveFeed(stories: List<Story>) {
        Completable.fromAction {
            stories.forEach { story ->
                dao.deleteStory(story)
                if (story.saved) dao.saveStory(story)
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }
}