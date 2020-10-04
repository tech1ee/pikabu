package com.example.pikabu.di.all

import com.example.pikabu.di.AllFeedScope
import com.example.pikabu.ui.feed.FeedFragment
import dagger.Subcomponent

@AllFeedScope
@Subcomponent(modules = [AllFeedModule::class])
interface AllFeedComponent {

    fun inject(fragment: FeedFragment)
}