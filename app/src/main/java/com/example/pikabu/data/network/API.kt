package com.example.pikabu.data.network

import com.example.pikabu.entity.Story
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface API {

    @GET("feed.php")
    fun getFeed(): Single<List<Story>>

    @GET("story.php")
    fun getStory(@Query("id") storyId: Int): Single<Story>
}