package com.example.pikabu.ui.story

import androidx.lifecycle.MutableLiveData
import com.example.pikabu.data.repository.Repository
import com.example.pikabu.ui.BaseViewModel

class StoryViewModel(private val repository: Repository): BaseViewModel() {

    val storyLiveData = MutableLiveData<StoryEvent>()

    fun getStory(storyId: Int) {
        event(StoryEvent.Progress(true))
        disposable.add(
        repository.getStory(storyId)
            .subscribe(
                {
                    event(StoryEvent.Progress(false))
                    event(StoryEvent.Data(it))
                },
                {
                    event(StoryEvent.Progress(false))
                }
            )
        )
    }

    fun changeStoryStatus(storyId: Int, save: Boolean) {
        repository.changeStoryStatus(storyId, save)
    }

    private fun event(event: StoryEvent) {
        storyLiveData.value = event
    }
}