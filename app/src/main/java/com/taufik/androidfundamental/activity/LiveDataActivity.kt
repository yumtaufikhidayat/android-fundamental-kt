package com.taufik.androidfundamental.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.taufik.androidfundamental.databinding.ActivityLiveDataBinding

class LiveDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLiveDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLiveDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}