package com.dicoding.submission2.model

import com.dicoding.submission2.data.local.datastore.SettingPreferences
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock


internal class SettingViewModelTest {
    private lateinit var settingViewModel: SettingViewModel
    private lateinit var settingPreferences: SettingPreferences
    private val dummyBool = true

@Before
fun before(){
    settingPreferences = mock(SettingPreferences::class.java)
    settingViewModel = SettingViewModel(settingPreferences)

}
    @Test
    fun getThemeSettings() {


    }
}