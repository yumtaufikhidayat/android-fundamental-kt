package com.taufik.androidfundamental

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.taufik.androidfundamental.databinding.ActivityMainBinding
import com.taufik.androidfundamental.debug.DebugActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionButton()
    }

    private fun setActionButton() {
        binding.apply {
            btnIntent.setOnClickListener {
                startActivity(Intent(this@MainActivity, DebugActivity::class.java))
            }
        }
    }
}
