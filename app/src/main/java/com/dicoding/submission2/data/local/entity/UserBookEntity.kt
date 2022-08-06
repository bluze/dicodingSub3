package com.dicoding.submission2.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userBook")
class UserBookEntity (
        @field:ColumnInfo(name = "login")
        @field:PrimaryKey
        val login: String,

        @field:ColumnInfo(name = "avatar_url")
        val avatarUrl: String,

        @field:ColumnInfo(name = "html_url")
        val htmlUrl: String? = null,

        @field:ColumnInfo(name = "bookmarked")
        var isBookmarked: Boolean

)