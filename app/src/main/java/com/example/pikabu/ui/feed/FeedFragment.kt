package com.example.pikabu.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pikabu.App
import com.example.pikabu.R
import com.example.pikabu.data.NoConnectionException
import com.example.pikabu.data.NoDataException
import com.example.pikabu.databinding.FragmentFeedBinding
import com.example.pikabu.entity.Feed
import com.example.pikabu.entity.Story
import com.example.pikabu.ui.story.StoryFragment
import javax.inject.Inject


class FeedFragment : Fragment() {

    @Inject
    lateinit var viewModel: FeedViewModel
    private lateinit var binding: FragmentFeedBinding
    private val adapter = FeedAdapter(
    { storyId -> showStory(storyId) },
    { storyId, save -> viewModel.changeStoryStatus(storyId, save) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val feedType = arguments?.getString(KEY_FEED_TYPE) ?: return
        inject(feedType)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeViewModel()
    }

    private fun setupView() {
        binding.feedRecyclerView.layoutManager = LinearLayoutManager(context,
            RecyclerView.VERTICAL, false)
        binding.feedRecyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.feedLiveData.observe(viewLifecycleOwner, { onEvent(it) })
        viewModel.getFeed()
    }

    private fun onEvent(event: FeedEvent) {
        when (event) {
            is FeedEvent.Progress -> {
                showMessage(false)
                showProgress(event.inProgress)
            }
            is FeedEvent.Error -> showMessage(true, event.throwable)
            is FeedEvent.Stories -> showFeed(event.stories)
        }
    }

    private fun showProgress(inProgress: Boolean) {
        binding.progressBar.visibility = if (inProgress) View.VISIBLE else View.GONE
    }

    private fun showFeed(stories: List<Story>) {
        adapter.setItems(stories)
    }

    private fun showMessage(show: Boolean, throwable: Throwable? = null) {
        when (throwable) {
            is NoConnectionException -> {
                binding.messageScreen.image.setImageResource(R.drawable.ic_no_signal)
                binding.messageScreen.title.text = getString(R.string.error)
                binding.messageScreen.message.text = getString(R.string.error_connection)
                binding.messageScreen.retryButton.setOnClickListener { viewModel.getFeed() }
                binding.messageScreen.retryButton.visibility = View.VISIBLE
            }
            is NoDataException -> {
                binding.messageScreen.image.setImageResource(R.drawable.ic_library_add)
                binding.messageScreen.title.text = getString(R.string.empty_list)
                binding.messageScreen.message.text = getString(R.string.empty_list_message)
                binding.messageScreen.retryButton.visibility = View.GONE
            }
            else -> {
                binding.messageScreen.image.setImageResource(R.drawable.ic_baseline_error)
                binding.messageScreen.title.text = getString(R.string.error)
                binding.messageScreen.message.text = getString(R.string.error_something_went_wrong)
                binding.messageScreen.retryButton.setOnClickListener { viewModel.getFeed() }
                binding.messageScreen.retryButton.visibility = View.VISIBLE
            }
        }
        binding.messageScreen.root.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun inject(feedType: String) {
        when (feedType) {
            Feed.ALL -> App.allFeedComponent(context)?.inject(this)
            Feed.SAVED -> App.savedFeedComponent(context)?.inject(this)
        }
    }

    private fun showStory(storyId: Int) {
        val fragment = StoryFragment.newInstance(storyId)
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container, fragment)
            ?.addToBackStack(null)
            ?.commit()
    }
    companion object {

        const val KEY_FEED_TYPE = "KEY_FEED_TYPE"

        fun newInstance(feedType: String): FeedFragment {
            return FeedFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_FEED_TYPE, feedType)
                }
            }
        }
    }
}