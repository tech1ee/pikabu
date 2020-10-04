package com.example.pikabu.ui.feed

import com.example.pikabu.entity.Story

sealed class FeedEvent {
    data class Progress(val inProgress: Boolean): FeedEvent()
    data class Error(val throwable: Throwable): FeedEvent()
    data class Stories(val stories: List<Story>): FeedEvent()
}