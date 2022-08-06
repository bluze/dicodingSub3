package com.dicoding.submission2.data.remote


import com.dicoding.submission2.BuildConfig
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @Headers("Authorization: token $API_KEY")
    @GET("search/users")
    fun searchUser(
        @Query("q") query: String
    ): Call<GitSearchResponse>

    @Headers("Authorization: token $API_KEY")
    @GET("search/users")
    suspend fun searchBUser(
        @Query("q") query: String
    ): GitSearchResponse

    @Headers("Authorization: token $API_KEY")
    @GET("users/{id}")
    fun listUser(
        @Path("id") id: String?
    ): Call<GitUserResponse>

    @Headers("Authorization: token $API_KEY")
    @GET("users/{id}/{tipe}")
    fun getFollow(
        @Path("id") id: String?,
        @Path("tipe") tipe: String
    ): Call<List<GithubUser>>

    companion object{
        private const val API_KEY: String = BuildConfig.API_KEY
    }
}