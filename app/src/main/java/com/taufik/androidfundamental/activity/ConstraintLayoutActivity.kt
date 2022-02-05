package com.taufik.androidfundamental.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.taufik.androidfundamental.databinding.ActivityConstraintLayoutBinding

class ConstraintLayoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConstraintLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConstraintLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Google Pixel"

        setOnClickButton()
    }

    private fun setOnClickButton(){
        binding.apply {
            btnBuy.setOnClickListener {
                Toast.makeText(this@ConstraintLayoutActivity, "Beli dong", Toast.LENGTH_SHORT).show()
            }
        }
    }
}