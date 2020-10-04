package com.example.pikabu.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pikabu.entity.Story

@Database(entities = [Story::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun dao(): Dao
}