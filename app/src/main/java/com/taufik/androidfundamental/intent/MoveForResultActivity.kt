package com.taufik.androidfundamental.intent

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.taufik.androidfundamental.R
import com.taufik.androidfundamental.databinding.ActivityMoveForResultBinding

class MoveForResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoveForResultBinding

    companion object {
        const val EXTRA_SELECTED_VALUE = "com.taufik.androidfundamental.intent.EXTRA_SELECTED_VALUE"
        const val RESULT_CODE = 1100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoveForResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showActionBar()
        setActionChoose()
    }

    private fun showActionBar() {
        supportActionBar?.apply {
            title = "Pindah Activity Result"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setActionChoose() {
        binding.apply {
            btnChoose.setOnClickListener {
                if (rgNumber.checkedRadioButtonId > 0) {
                    var value = 0
                    when (rgNumber.checkedRadioButtonId) {
                        R.id.rb50 -> value = 50
                        R.id.rb100 -> value = 100
                        R.id.rb150 -> value = 150
                        R.id.rb200 -> value = 200
                    }

                    val resultIntent = Intent().apply {
                        putExtra(EXTRA_SELECTED_VALUE, value)
                    }
                    setResult(RESULT_CODE, resultIntent)
                    finish()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}