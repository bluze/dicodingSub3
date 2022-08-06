package com.dicoding.submission2.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submission2.data.remote.GitUserResponse
import com.dicoding.submission2.data.remote.GithubUser
import com.dicoding.submission2.data.remote.ItemsItem

class MainViewModel : ViewModel() {
    val mSearch = MutableLiveData<List<ItemsItem>>()
    val search: LiveData<List<ItemsItem>> = mSearch
    val mList = MutableLiveData<GitUserResponse>()
    val list: LiveData<GitUserResponse> = mList
    val mFollowers = MutableLiveData<List<GithubUser>>()
    val followers: LiveData<List<GithubUser>> = mFollowers
    val mFollowing = MutableLiveData<List<GithubUser>>()
    val following: LiveData<List<GithubUser>> = mFollowing
    val mIsLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = mIsLoading
    val mIsLoading1 = MutableLiveData<Boolean>()
    val isLoading1: LiveData<Boolean> = mIsLoading1

}

