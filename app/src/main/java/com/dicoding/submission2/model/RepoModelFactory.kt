package com.dicoding.submission2.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.submission2.data.UserBookRepository
import com.dicoding.submission2.di.Injection


class RepoModelFactory private constructor(private val userBookRepository: UserBookRepository) :
        ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RepoViewModel::class.java)) {
                return RepoViewModel(userBookRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }

        companion object {
            @Volatile
            private var instance: RepoModelFactory? = null
            fun getInstance(context: Context): RepoModelFactory =
                instance ?: synchronized(this) {
                    instance ?: RepoModelFactory(Injection.provideRepository(context))
                }.also { instance = it }
        }
    }
