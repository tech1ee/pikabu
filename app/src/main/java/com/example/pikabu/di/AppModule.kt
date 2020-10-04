package com.example.pikabu.di

import android.app.Application
import android.content.Context
import com.example.pikabu.data.repository.Repository
import com.example.pikabu.ui.story.StoryViewModel
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val app: Application) {

    @Provides
    fun provideContext(): Context {
        return app.applicationContext
    }

    @Provides
    fun provideStoryViewModel(repository: Repository): StoryViewModel {
        return StoryViewModel(repository)
    }
}