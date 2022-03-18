package com.taufik.androidfundamental.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.taufik.androidfundamental.databinding.ActivityOptionMenuBinding

class OptionMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOptionMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOptionMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}