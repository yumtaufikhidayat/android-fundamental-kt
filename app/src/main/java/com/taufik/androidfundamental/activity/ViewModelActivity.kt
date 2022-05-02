package com.taufik.androidfundamental.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.taufik.androidfundamental.databinding.ActivityViewModelBinding
import com.taufik.androidfundamental.viewmodel.MainViewModel

class ViewModelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewModelBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewModelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        displayResult()
        setAction()
    }

    private fun setAction() = with(binding){
        btnCalculate.setOnClickListener {
            val width = etWidth.text.toString()
            val length = etLength.text.toString()
            val height = etHeight.text.toString()

            when {
                width.isEmpty() -> {
                    etWidth.error = "Field tidak boleh kosong"
                }

                length.isEmpty() -> {
                    etLength.error = "Field tidak boleh kosong"
                }

                height.isEmpty() -> {
                    etHeight.error = "Field tidak boleh kosong"
                }

                else -> {
                    viewModel.calculate(width, length, height)
                    displayResult()
                }
            }
        }
    }

    private fun displayResult() = with(binding) {
        tvResult.text = viewModel.result.toString()
    }
}