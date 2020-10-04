package com.example.pikabu.ui.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.pikabu.R
import com.example.pikabu.entity.Feed
import com.example.pikabu.ui.feed.FeedFragment

class FeedPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val fragments = arrayListOf<Fragment>(
        FeedFragment.newInstance(Feed.ALL),
        FeedFragment.newInstance(Feed.SAVED)
    )
    private val titles = arrayListOf(
        fragment.getString(R.string.tab_all_posts),
        fragment.getString(R.string.tab_saved_posts)
    )

    fun getTitle(position: Int): String {
        return titles[position]
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}