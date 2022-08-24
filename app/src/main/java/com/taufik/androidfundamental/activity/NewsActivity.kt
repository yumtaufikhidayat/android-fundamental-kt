package com.taufik.androidfundamental.activity

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.taufik.androidfundamental.R
import com.taufik.androidfundamental.adapter.news.NewsPagerAdapter
import com.taufik.androidfundamental.databinding.ActivityNewsBinding

class NewsActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityNewsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initActionBar()
        initTabLayout()
    }

    private fun initActionBar(){
        supportActionBar?.elevation = 0f
    }

    private fun initTabLayout() = with(binding) {
        val newsPagerAdapter = NewsPagerAdapter(this@NewsActivity)
        viewPager.adapter = newsPagerAdapter
        TabLayoutMediator(
            tabsNews, viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.home, R.string.bookmark)
    }
}