package com.taufik.androidfundamental.activity

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.taufik.androidfundamental.R
import com.taufik.androidfundamental.adapter.SectionsPagerAdapter
import com.taufik.androidfundamental.databinding.ActivityTabLayoutBinding

class TabLayoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTabLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTabLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.elevation = 0F
        setData()
    }

    private fun setData() {
        binding.apply {
            val sectionsPagerAdapter = SectionsPagerAdapter(this@TabLayoutActivity)
            viewPager.adapter = sectionsPagerAdapter
            TabLayoutMediator(tabLayout, viewPager) { tabs, position ->
                tabs.text = resources.getString(tabTitles[position])
            }.attach()
        }
    }

    companion object {
        @StringRes
        private val tabTitles = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}