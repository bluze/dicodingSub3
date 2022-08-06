package com.dicoding.submission2.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.submission2.data.local.entity.UserBookEntity

@Dao
interface UserBookDao {
        @Query("SELECT * FROM userBook ORDER BY login DESC")
        fun getUser(): LiveData<List<UserBookEntity>>

        @Query("SELECT * FROM userBook where bookmarked = 1")
        fun getBookmarkedUser(): LiveData<List<UserBookEntity>>

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        suspend fun insertUser(UserBook: List<UserBookEntity>)

        @Update
        suspend fun updateUser(UserBook: UserBookEntity)

        @Query("DELETE FROM userBook WHERE bookmarked = 0")
        suspend fun deleteAll()

        @Query("SELECT EXISTS(SELECT * FROM userBook WHERE login = :login AND bookmarked = 1)")
        suspend fun isUserBookmarked(login: String): Boolean

}

