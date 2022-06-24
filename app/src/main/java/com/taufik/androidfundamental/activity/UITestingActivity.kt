package com.taufik.androidfundamental.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.taufik.androidfundamental.R
import com.taufik.androidfundamental.data.CuboidModel
import com.taufik.androidfundamental.databinding.ActivityUiTestingBinding
import com.taufik.androidfundamental.viewmodel.UnitTestingViewModel

class UITestingActivity : AppCompatActivity(), View.OnClickListener {

    private val binding by lazy {
        ActivityUiTestingBinding.inflate(layoutInflater)
    }

    private lateinit var unitTestingViewModel: UnitTestingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViewModel()
        setAction()
    }

    private fun initViewModel() {
        unitTestingViewModel = UnitTestingViewModel(CuboidModel())
    }

    private fun setAction() = with(binding) {
        btnSave.setOnClickListener(this@UITestingActivity)
        btnCalculateVolume.setOnClickListener(this@UITestingActivity)
        btnCalculateSurfaceArea.setOnClickListener(this@UITestingActivity)
        btnCalculateCircumference.setOnClickListener(this@UITestingActivity)
    }

    private fun isVisibility(isShow: Boolean) = with(binding) {
        btnSave.visibility = if (isShow) {
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
            val length = etLength.text.toString().trim()
            val width = etWidth.text.toString().trim()
            val height = etHeight.text.toString().trim()

            when {
                TextUtils.isEmpty(length) -> etLength.error = "Field ini tidak boleh kosong"
                TextUtils.isEmpty(width) -> etWidth.error = "Field ini tidak boleh kosong"
                TextUtils.isEmpty(height) -> etHeight.error = "Field ini tidak boleh kosong"

                else -> {
                    val valueLength = length.toDouble()
                    val valueWidth = width.toDouble()
                    val valueHeight = height.toDouble()

                    when (v.id) {
                        R.id.btnSave -> {
                            unitTestingViewModel.save(valueLength, valueWidth, valueHeight)
                            isVisibility(true)
                        }

                        R.id.btnCalculateVolume -> {
                            val volume = unitTestingViewModel.getVolume().toString()
                            tvResult.text = volume
                            isVisibility(false)
                        }

                        R.id.btnCalculateCircumference -> {
                            val circumference = unitTestingViewModel.getCircumference().toString()
                            tvResult.text = circumference
                            isVisibility(false)
                        }

                        R.id.btnCalculateSurfaceArea -> {
                            val surfaceArea = unitTestingViewModel.getSurfaceArea().toString()
                            tvResult.text = surfaceArea
                            isVisibility(false)
                        }
                    }
                }
            }
        }
    }
}