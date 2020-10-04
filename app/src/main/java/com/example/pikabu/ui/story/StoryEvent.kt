package com.example.pikabu.ui.story

import com.example.pikabu.entity.Story

sealed class StoryEvent {
    data class Progress(val inProgress: Boolean): StoryEvent()
    data class Data(val story: Story): StoryEvent()
}