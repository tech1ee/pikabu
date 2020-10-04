package com.example.pikabu.ui.story

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.pikabu.App
import com.example.pikabu.R
import com.example.pikabu.databinding.FragmentStoryBinding
import com.example.pikabu.entity.Story
import javax.inject.Inject

class StoryFragment: Fragment() {

    @Inject
    lateinit var viewModel: StoryViewModel
    private lateinit var binding: FragmentStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent(context)?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        val storyId = arguments?.getInt(STORY_ID) ?: return
        viewModel.getStory(storyId)
    }

    private fun observeViewModel() {
        viewModel.storyLiveData.observe(viewLifecycleOwner, { onEvent(it) })
    }

    private fun onEvent(event: StoryEvent) {
        when (event) {
            is StoryEvent.Progress -> showProgress(event.inProgress)
            is StoryEvent.Data -> showStory(event.story)
        }
    }

    private fun showProgress(inProgress: Boolean) {
        binding.progressBar.visibility = if (inProgress) View.VISIBLE else View.GONE
    }

    private fun showStory(story: Story) {
        setTitle(story)
        setBody(story)
        setImage(story)
        setClicks(story)
        setButton(story)
    }

    private fun setTitle(story: Story) {
        val title: String? = story.title ?: return
        binding.title.text = title
    }

    private fun setBody(story: Story) {
        val body: String? = story.body ?: return
        binding.body.text = body
        binding.body.visibility = View.VISIBLE
    }

    private fun setImage(story: Story) {
        if (story.images.isNullOrEmpty()) return
        val image: String? = story.images[0]
        Glide.with(binding.root.context).load(image).into(binding.image)
        binding.image.visibility = View.VISIBLE
    }

    private fun setClicks(story: Story) {
        binding.saveButton.setOnClickListener {
            val id = story.id ?: return@setOnClickListener
            val saved = !story.saved
            viewModel.changeStoryStatus(id, saved)
            story.saved = saved
            setButton(story)
        }
    }

    private fun setButton(story: Story) {
        if (story.saved) {
            binding.saveButton.text = context?.getString(R.string.remove_from_saved)
            binding.saveButton.setBackgroundColor(
                ContextCompat.getColor(requireContext(), android.R.color.holo_green_light)
            )
        } else {
            binding.saveButton.text = context?.getString(R.string.save)
            binding.saveButton.setBackgroundColor(
                ContextCompat.getColor(requireContext(), android.R.color.holo_purple)
            )
        }
        binding.saveButton.visibility = View.VISIBLE
    }

    companion object {

        private const val STORY_ID = "STORY_ID"

        fun newInstance(storyId: Int): StoryFragment {
            return StoryFragment().apply {
                arguments = Bundle().apply {
                    putInt(STORY_ID, storyId)
                }
            }
        }
    }
}