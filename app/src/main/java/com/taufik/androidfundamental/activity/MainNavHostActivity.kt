package com.taufik.androidfundamental.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.taufik.androidfundamental.databinding.ActivityMainNavHostBinding

class MainNavHostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainNavHostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainNavHostBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}