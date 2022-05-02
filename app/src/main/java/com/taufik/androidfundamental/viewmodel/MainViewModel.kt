package com.taufik.androidfundamental.viewmodel

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var result = 0.0

    fun calculate(width: String, length: String, height: String) {
        result = width.toDouble() * length.toDouble() * height.toDouble()
    }
}