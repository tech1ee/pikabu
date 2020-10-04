package com.example.pikabu.di

import com.example.pikabu.App
import com.example.pikabu.di.all.AllFeedComponent
import com.example.pikabu.di.all.AllFeedModule
import com.example.pikabu.di.saved.SavedFeedComponent
import com.example.pikabu.di.saved.SavedFeedModule
import com.example.pikabu.ui.story.StoryFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class, RepositoryModule::class])
interface AppComponent {

    fun createAllFeedComponent(module: AllFeedModule): AllFeedComponent

    fun createSavedFeedComponent(module: SavedFeedModule): SavedFeedComponent

    fun inject(app: App)

    fun inject(fragment: StoryFragment)
}