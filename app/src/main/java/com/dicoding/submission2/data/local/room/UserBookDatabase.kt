package com.dicoding.submission2.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.submission2.data.local.entity.UserBookEntity
import com.dicoding.submission2.ui.MainActivity

@Database(entities = [UserBookEntity::class], version = 1, exportSchema = false)
abstract class UserBookDatabase : RoomDatabase() {
        abstract fun userBookDao(): UserBookDao

        companion object {
            @Volatile
            private var instance: UserBookDatabase? = null
            fun getInstance(context: Context): UserBookDatabase =
                instance ?: synchronized(this) {
                    instance ?: Room.databaseBuilder(
                        context.applicationContext,
                        UserBookDatabase::class.java, "UserBook"
                    ).build()
                }
        }

}

