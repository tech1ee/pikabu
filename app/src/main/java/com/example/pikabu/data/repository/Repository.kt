package com.example.pikabu.data.repository

import com.example.pikabu.entity.Story
import io.reactivex.Observable
import io.reactivex.Single

interface Repository {

    fun getFeed(feedType: String): Observable<List<Story>>

    fun getStory(storyId: Int): Single<Story>

    fun changeStoryStatus(storyId: Int, save: Boolean)
}