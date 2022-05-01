package com.taufik.androidfundamental.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.taufik.androidfundamental.databinding.ActivityViewModelBinding

class ViewModelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewModelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewModelBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}