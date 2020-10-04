package com.example.pikabu.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.pikabu.entity.Story
import io.reactivex.Single

@Dao
interface Dao {

    @Query("SELECT * FROM story")
    fun getFeed(): Single<List<Story>>

    @Insert
    fun saveStory(story: Story)

    @Delete
    fun deleteStory(story: Story)
}