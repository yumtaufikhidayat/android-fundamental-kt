package com.taufik.androidfundamental.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.taufik.androidfundamental.R
import com.taufik.androidfundamental.data.CuboidModel
import com.taufik.androidfundamental.databinding.ActivityTestingBinding
import com.taufik.androidfundamental.viewmodel.UnitTestingViewModel

class TestingActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityTestingBinding
    private lateinit var unitTestingViewModel: UnitTestingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        setAction()
    }

    private fun initViewModel() {
        unitTestingViewModel = UnitTestingViewModel(CuboidModel())
    }

    private fun setAction() = with(binding) {
        btnSaveTest.setOnClickListener(this@TestingActivity)
        btnCalculateVolumeTest.setOnClickListener(this@TestingActivity)
        btnCalculateSurfaceAreaTest.setOnClickListener(this@TestingActivity)
        btnCalculateCircumferenceTest.setOnClickListener(this@TestingActivity)
    }

    private fun isVisibility(isShow: Boolean) = with(binding) {
        btnSaveTest.visibility = if (isShow) {
            View.GONE
        } else {
            View.VISIBLE
        }

        btnVisibility.visibility = if (isShow) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    override fun onClick(v: View) {
        binding.apply {
            val length = etLengthTest.text.toString().trim()
            val width = etWidthTest.text.toString().trim()
            val height = etHeightTest.text.toString().trim()

            when {
                TextUtils.isEmpty(length) -> etLengthTest.error = "Field ini tidak boleh kosong"
                TextUtils.isEmpty(width) -> etWidthTest.error = "Field ini tidak boleh kosong"
                TextUtils.isEmpty(height) -> etHeightTest.error = "Field ini tidak boleh kosong"

                else -> {
                    val valueLength = length.toDouble()
                    val valueWidth = width.toDouble()
                    val valueHeight = height.toDouble()

                    when (v.id) {
                        R.id.btnSaveTest -> {
                            unitTestingViewModel.save(valueLength, valueWidth, valueHeight)
                            isVisibility(true)
                        }

                        R.id.btnCalculateVolumeTest -> {
                            val volume = unitTestingViewModel.getVolume().toString()
                            tvResultTest.text = volume
                            isVisibility(false)
                        }

                        R.id.btnCalculateCircumferenceTest -> {
                            val circumference = unitTestingViewModel.getCircumference().toString()
                            tvResultTest.text = circumference
                            isVisibility(false)
                        }

                        R.id.btnCalculateSurfaceAreaTest -> {
                            val surfaceArea = unitTestingViewModel.getSurfaceArea().toString()
                            tvResultTest.text = surfaceArea
                            isVisibility(false)
                        }
                    }
                }
            }
        }
    }
}