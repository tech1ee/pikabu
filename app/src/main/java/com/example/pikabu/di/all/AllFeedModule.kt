package com.example.pikabu.di.all

import com.example.pikabu.data.repository.Repository
import com.example.pikabu.di.AllFeedScope
import com.example.pikabu.ui.feed.AllFeedViewModel
import com.example.pikabu.ui.feed.FeedViewModel
import dagger.Module
import dagger.Provides

@Module
class AllFeedModule {

    @AllFeedScope
    @Provides
    fun provideAllFeedViewModel(repository: Repository): FeedViewModel {
        return AllFeedViewModel(repository)
    }
}