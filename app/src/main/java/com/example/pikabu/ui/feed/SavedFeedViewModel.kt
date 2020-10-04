package com.example.pikabu.ui.feed

import com.example.pikabu.data.repository.Repository
import com.example.pikabu.entity.Feed

class SavedFeedViewModel(repository: Repository): FeedViewModel(repository) {

    override fun getFeed() { getFeed(Feed.SAVED) }
}