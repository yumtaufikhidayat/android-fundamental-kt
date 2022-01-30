package com.taufik.androidfundamental.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.taufik.androidfundamental.databinding.ActivityVolumeBinding

class VolumeActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityVolumeBinding

    companion object {
        private const val TAG = "MAIN_ACTIVITY"
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVolumeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.i(TAG, "onCreate: ")

        if (savedInstanceState != null) {
            binding.apply {
                val volume = savedInstanceState.getString(TAG)
                tvResult.text = volume
            }
        }

        result()
    }

    private fun result() {
        binding.apply {
            btnResult.setOnClickListener {

                var isEmptyField = false

                val length = etLength.text.toString().trim()
                val width = etWidth.text.toString().trim()
                val height = etHeight.text.toString().trim()

                if (length.isEmpty()) {
                    isEmptyField = true
                    etLength.error = "Field ini tidak boleh kosong"
                }

                if (width.isEmpty()) {
                    isEmptyField = true
                    etWidth.error = "Field ini tidak boleh kosong"
                }

                if (height.isEmpty()) {
                    isEmptyField = true
                    etHeight.error = "Field ini tidak boleh kosong"
                }

                if (!isEmptyField) {
                    val volume = length.toDouble() * width.toDouble() * height.toDouble()
                    tvResult.text = volume.toString()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop: ")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy: ")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.apply {
            outState.putString(TAG, tvResult.text.toString())
        }

        Log.i(TAG, "onSaveInstanceState: ")
    }
}