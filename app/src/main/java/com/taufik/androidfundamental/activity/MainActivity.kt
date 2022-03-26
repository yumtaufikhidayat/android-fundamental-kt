package com.taufik.androidfundamental.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.taufik.androidfundamental.R
import com.taufik.androidfundamental.databinding.ActivityMainBinding
import com.taufik.androidfundamental.fragment.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    
    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showHomeFragment()
    }

    private fun showHomeFragment() {
        val mFragmentManager = supportFragmentManager
        val mHomeFragment = HomeFragment()
        val fragment = mFragmentManager.findFragmentByTag(HomeFragment::class.java.simpleName)

        if (fragment !is HomeFragment) {
            Log.i(TAG, "Fragment name: ${HomeFragment::class.java.simpleName}")
            mFragmentManager.beginTransaction()
                .add(R.id.frameContainer, mHomeFragment, HomeFragment::class.java.simpleName)
                .commit()
        }
    }
}
