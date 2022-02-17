package com.taufik.androidfundamental.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.taufik.androidfundamental.databinding.ActivityCustomButtonEditTextBinding

class CustomButtonEditTextActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCustomButtonEditTextBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomButtonEditTextBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButtonEnabled()
    }

    private fun setCustomView() {
        binding.apply {
            val result = etCustomEditText.text
            btnCustomButton.isEnabled = result != null && result.toString().isNotEmpty()
        }
    }

    private fun setButtonEnabled() {
        binding.apply {
            etCustomEditText.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    // Do nothing
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    setCustomView()
                }

                override fun afterTextChanged(s: Editable?) {
                    // Do nothing
                }
            })
            
            btnCustomButton.setOnClickListener {
                Toast.makeText(this@CustomButtonEditTextActivity, etCustomEditText.text, Toast.LENGTH_SHORT).show()
            }
        }
    }
}