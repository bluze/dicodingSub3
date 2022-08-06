package com.dicoding.submission2.data.remote

import com.google.gson.annotations.SerializedName

data class GitSearchResponse(

	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean,

	@field:SerializedName("items")
	val items: List<ItemsItem>
)

data class ItemsItem(

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("html_url")
	val htmlUrl: String,

)

data class GitUserResponse(

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("company")
	val company: String,

	@field:SerializedName("followers")
	val followers: Int,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("html_url")
	val htmlUrl: String,

	@field:SerializedName("following")
	val following: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("location")
	val location: String,

)

data class GithubUser (

	@SerializedName("login"               ) var login             : String?  = null,
	@SerializedName("avatar_url"          ) var avatarUrl         : String?  = null,
	@SerializedName("html_url"            ) var htmlUrl           : String?  = null,

	)
