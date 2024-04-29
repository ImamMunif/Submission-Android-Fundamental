package com.example.github_user_app.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.dixam.data.SettingPreferences
import com.dicoding.dixam.ui.settings.SettingsViewModel

class SettingsViewModelFactory(private val preference: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(preference) as T
        }
        throw IllegalArgumentException("Invalid reference view model class: " + modelClass.name)
    }
}