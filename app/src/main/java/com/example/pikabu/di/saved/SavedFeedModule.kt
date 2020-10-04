package com.example.pikabu.di.saved

import com.example.pikabu.data.repository.Repository
import com.example.pikabu.di.SavedFeedScope
import com.example.pikabu.ui.feed.FeedViewModel
import com.example.pikabu.ui.feed.SavedFeedViewModel
import dagger.Module
import dagger.Provides

@Module
class SavedFeedModule {

    @SavedFeedScope
    @Provides
    fun provideFeedViewModel(repository: Repository): FeedViewModel {
        return SavedFeedViewModel(repository)
    }
}