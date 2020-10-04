package com.example.pikabu.ui.feed

import androidx.lifecycle.MutableLiveData
import com.example.pikabu.data.repository.Repository
import com.example.pikabu.ui.BaseViewModel

abstract class FeedViewModel(
    protected val repository: Repository
): BaseViewModel() {

    val feedLiveData = MutableLiveData<FeedEvent>()

    abstract fun getFeed()

    fun changeStoryStatus(storyId: Int, save: Boolean) {
        repository.changeStoryStatus(storyId, save)
    }

    protected fun getFeed(feedType: String) {
        event(FeedEvent.Progress(true))
        disposable.add(
            repository.getFeed(feedType)
                .subscribe(
                    {
                        event(FeedEvent.Progress(false))
                        event(FeedEvent.Stories(it))
                    },
                    {
                        event(FeedEvent.Progress(false))
                        event(FeedEvent.Error(it))
                    }
                )
        )
    }

    private fun event(event: FeedEvent) {
        feedLiveData.value = event
    }
}