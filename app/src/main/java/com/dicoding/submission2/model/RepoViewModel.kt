package com.dicoding.submission2.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.submission2.data.UserBookRepository
import com.dicoding.submission2.data.local.entity.UserBookEntity
import kotlinx.coroutines.launch

class RepoViewModel(private val userBookRepository: UserBookRepository) : ViewModel() {
    fun setBookmarkList(query: String) = userBookRepository.setBookmarkList(query)

    fun getBookmarkedUser() = userBookRepository.getBookmarkedUser()

    fun saveUser(user: UserBookEntity) {
        viewModelScope.launch {
            userBookRepository.setUserBookmark(user, true)
        }
    }

    fun deleteUser(user: UserBookEntity) {
        viewModelScope.launch {
            userBookRepository.setUserBookmark(user, false)
        }
    }
}