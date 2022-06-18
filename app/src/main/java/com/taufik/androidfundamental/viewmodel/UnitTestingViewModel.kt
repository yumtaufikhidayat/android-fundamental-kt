package com.taufik.androidfundamental.viewmodel

import com.taufik.androidfundamental.data.CuboidModel

class UnitTestingViewModel(private val cuboidModel: CuboidModel) {

    fun getCircumference() = cuboidModel.getCircumference()

    fun getSurfaceArea() = cuboidModel.getSurfaceArea()

    fun getVolume() = cuboidModel.getVolume()

    fun save(w: Double, l: Double, h: Double) {
        cuboidModel.save(w, l, h)
    }
}