package com.taufik.androidfundamental.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.taufik.androidfundamental.databinding.ActivityCustomButtonEditTextBinding

class CustomButtonEditTextActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCustomButtonEditTextBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomButtonEditTextBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}