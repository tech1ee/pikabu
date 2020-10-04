package com.example.pikabu.di.saved

import com.example.pikabu.di.SavedFeedScope
import com.example.pikabu.ui.feed.FeedFragment
import dagger.Subcomponent

@SavedFeedScope
@Subcomponent(modules = [SavedFeedModule::class])
interface SavedFeedComponent {

    fun inject(fragment: FeedFragment)
}