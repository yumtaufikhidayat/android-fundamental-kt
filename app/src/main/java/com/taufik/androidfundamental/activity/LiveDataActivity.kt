package com.taufik.androidfundamental.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.taufik.androidfundamental.R
import com.taufik.androidfundamental.databinding.ActivityLiveDataBinding
import com.taufik.androidfundamental.viewmodel.LiveDataViewModel

class LiveDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLiveDataBinding
    private val liveDataViewModel: LiveDataViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLiveDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        subscribe()
    }

    private fun subscribe() = with(binding) {
        val elapsedTimeObserver = Observer<Long?> { aLong ->
            val newText = this@LiveDataActivity.resources.getString(R.string.tvSeconds, aLong)
            tvTimer.text = newText
        }

        liveDataViewModel.getElapsedTime().observe(this@LiveDataActivity, elapsedTimeObserver)
    }
}