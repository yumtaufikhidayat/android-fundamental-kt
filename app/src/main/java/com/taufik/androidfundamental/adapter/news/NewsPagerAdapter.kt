package com.taufik.androidfundamental.adapter.news

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.taufik.androidfundamental.fragment.NewsFragment

class NewsPagerAdapter internal constructor(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        val fragment = NewsFragment()
        val bundle = Bundle()
        if (position == 0) {
            bundle.putString(NewsFragment.ARG_TAB, NewsFragment.TAB_NEWS)
        } else {
            bundle.putString(NewsFragment.ARG_TAB, NewsFragment.TAB_BOOKMARK)
        }
        fragment.arguments = bundle
        return fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}