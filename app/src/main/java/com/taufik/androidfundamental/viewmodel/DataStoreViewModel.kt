package com.taufik.androidfundamental.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.taufik.androidfundamental.preference.SettingPreferences
import kotlinx.coroutines.launch

class DataStoreViewModel(private val pref: SettingPreferences) : ViewModel() {
    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
}