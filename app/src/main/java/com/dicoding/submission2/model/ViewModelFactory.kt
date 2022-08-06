package com.dicoding.submission2.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.submission2.data.local.datastore.SettingPreferences


class ViewModelFactory(private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {

   @Suppress("UNCHECKED_CAST")
   override fun <T : ViewModel> create(modelClass: Class<T>): T {
       if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
           return SettingViewModel(pref) as T
        }
       throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
   }
}