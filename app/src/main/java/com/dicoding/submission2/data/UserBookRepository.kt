package com.dicoding.submission2.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.dicoding.submission2.data.local.entity.UserBookEntity
import com.dicoding.submission2.data.local.room.UserBookDao
import com.dicoding.submission2.data.remote.ApiService

class UserBookRepository constructor (
    private val apiService: ApiService,
    private val userBookDao: UserBookDao
    ) {
        fun setBookmarkList(query : String): LiveData<Result<List<UserBookEntity>>> = liveData {
            emit(Result.Loading)
            try {
                val response = apiService.searchBUser(query)
                val itemss = response.items
                val userList = itemss.map { items ->
                    val isBookmarked = userBookDao.isUserBookmarked(items.login)
                    UserBookEntity(
                        items.login,
                        items.avatarUrl,
                        items.htmlUrl,
                        isBookmarked
                    )
                }
                userBookDao.deleteAll()
                userBookDao.insertUser(userList)
            } catch (e: Exception) {
                Log.d("Search", "onFailure:  ${e.message.toString()} ")
                emit(Result.Error(e.message.toString()))
            }
            val localData: LiveData<Result<List<UserBookEntity>>> = userBookDao.getUser().map { Result.Success(it) }
            emitSource(localData)
        }

        fun getBookmarkedUser(): LiveData<List<UserBookEntity>> {
            return userBookDao.getBookmarkedUser()
        }

        suspend fun setUserBookmark(user: UserBookEntity, bookmarkState: Boolean) {
            user.isBookmarked = bookmarkState
            userBookDao.updateUser(user)
        }

        companion object {
            @Volatile
            private var instance: UserBookRepository? = null
            fun getInstance(
                apiService: ApiService,
                userBookDao: UserBookDao
            ): UserBookRepository =
                instance ?: synchronized(this) {
                    instance ?: UserBookRepository(apiService, userBookDao)
                }.also { instance = it }
        }

    }