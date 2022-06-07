package com.taufik.androidfundamental.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.taufik.androidfundamental.databinding.ActivityKtxfragmentKtxactivityBinding

class ActivityKTXFragmentKTXActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKtxfragmentKtxactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKtxfragmentKtxactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}