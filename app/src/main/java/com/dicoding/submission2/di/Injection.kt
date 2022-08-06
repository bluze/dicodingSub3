package com.dicoding.submission2.di

import android.content.Context
import com.dicoding.submission2.data.UserBookRepository
import com.dicoding.submission2.data.local.room.UserBookDatabase
import com.dicoding.submission2.data.remote.ApiConfig

object Injection {
    fun provideRepository(context: Context): UserBookRepository {
        val apiService = ApiConfig.getApiService()
        val database = UserBookDatabase.getInstance(context)
        val dao = database.userBookDao()
        return UserBookRepository.getInstance(apiService, dao)
    }
}