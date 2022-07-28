package com.taufik.androidfundamental.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.taufik.androidfundamental.preference.SettingPreferences
import com.taufik.androidfundamental.viewmodel.DataStoreViewModel

class ViewModelFactory(private val pref: SettingPreferences): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DataStoreViewModel::class.java) -> DataStoreViewModel(pref) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}