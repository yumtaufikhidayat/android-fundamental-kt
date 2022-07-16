package com.taufik.androidfundamental.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.taufik.androidfundamental.R
import com.taufik.androidfundamental.databinding.ActivitySettingsSharedPreferencesBinding
import com.taufik.androidfundamental.fragment.PreferenceFragment

class SettingsSharedPreferencesActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivitySettingsSharedPreferencesBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setFragment()
    }

    private fun setFragment() {
        val mFragmentManager = supportFragmentManager
        val preferenceFragment = PreferenceFragment()
        val fragment = mFragmentManager.findFragmentByTag(PreferenceFragment::class.java.simpleName)

        if (fragment !is PreferenceFragment) {
            mFragmentManager.commit {
                add(
                    R.id.settingHolder,
                    preferenceFragment,
                    PreferenceFragment::class.java.simpleName
                )
            }
        }
    }
}