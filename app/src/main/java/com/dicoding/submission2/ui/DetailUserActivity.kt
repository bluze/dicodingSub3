package com.dicoding.submission2.ui


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.submission2.data.remote.GitUserResponse
import com.dicoding.submission2.R
import com.dicoding.submission2.adapter.SectionsPagerAdapter
import com.dicoding.submission2.databinding.ActivityDetailUserBinding
import com.dicoding.submission2.model.MainViewModel
import com.dicoding.submission2.data.remote.ApiConfig
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private val mainViewModel by viewModels<MainViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {

        supportActionBar?.title = getString(R.string.detail_name)
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val tvUsername: TextView = binding.tvUserName
        tvUsername.text = intent.getStringExtra(EXTRA_USER)

        Glide.with(this).load(intent.getStringExtra(EXTRA_IMG)).circleCrop()
            .into(binding.imgUserPhoto)

        if (mainViewModel.mList.value == null) {
            findUser()
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        mainViewModel.list.observe(this) { list ->
            setDetailUser(list)
        }

    }


    private fun findUser() {
        mainViewModel.mIsLoading.value = true
        val userID = intent.getStringExtra(EXTRA_USER)
        val client = ApiConfig.getApiService().listUser(userID)
        client.enqueue(object : Callback<GitUserResponse> {
            override fun onResponse(
                call: Call<GitUserResponse>,
                response: Response<GitUserResponse>
            ) {
                mainViewModel.mIsLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        mainViewModel.mList.value = response.body()
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GitUserResponse>, t: Throwable) {
                mainViewModel.mIsLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    private fun setDetailUser(data: GitUserResponse) {
        binding.tvDetailName.text = data.name
        if (data.company != null) {
            binding.tvCompany.text = getString(R.string.company, data.company)
        }
        if (data.location != null) {
            binding.tvLocation.text = getString(R.string.location, data.location)
        }
        val tvLink: TextView = binding.tvLink
        tvLink.text = intent.getStringExtra(EXTRA_LINK)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        private const val TAG = "DetailUserActivity"
        const val EXTRA_USER = "extra_user"
        const val EXTRA_LINK = "extra_link"
        const val EXTRA_IMG = "extra_img"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}