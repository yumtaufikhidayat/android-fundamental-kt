package com.taufik.androidfundamental.activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.taufik.androidfundamental.databinding.ActivityDatastoreBinding
import com.taufik.androidfundamental.factory.ViewModelFactory
import com.taufik.androidfundamental.preference.SettingPreferences
import com.taufik.androidfundamental.viewmodel.DataStoreViewModel

class DataStoreActivity : AppCompatActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    private val binding by lazy {
        ActivityDatastoreBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSwitchTheme()
    }

    private fun setSwitchTheme() = with(binding) {

        val pref = SettingPreferences.getInstance(dataStore)
        val dataStoreViewModel = ViewModelProvider(
            this@DataStoreActivity,
            ViewModelFactory(pref)
        )[DataStoreViewModel::class.java]

        dataStoreViewModel.getThemeSettings().observe(this@DataStoreActivity) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            dataStoreViewModel.saveThemeSetting(isChecked)
        }
    }
}