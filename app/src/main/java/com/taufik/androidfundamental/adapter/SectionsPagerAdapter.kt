package com.taufik.androidfundamental.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.taufik.androidfundamental.fragment.HomeTabLayoutFragment
import com.taufik.androidfundamental.fragment.ProfileTabLayoutFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = HomeTabLayoutFragment()
            1 -> fragment = ProfileTabLayoutFragment()
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int = 2
}
