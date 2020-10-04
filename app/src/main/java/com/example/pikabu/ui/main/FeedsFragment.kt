package com.example.pikabu.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pikabu.databinding.FragmentFeedsBinding
import com.google.android.material.tabs.TabLayoutMediator

class FeedsFragment: Fragment() {

    private lateinit var binding: FragmentFeedsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = FeedPagerAdapter(this)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = adapter.getTitle(position)
        }.attach()
    }
}