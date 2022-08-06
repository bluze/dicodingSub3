package com.dicoding.submission2.ui


import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission2.data.remote.GitSearchResponse
import com.dicoding.submission2.data.remote.ItemsItem
import com.dicoding.submission2.R
import com.dicoding.submission2.adapter.SearchUserAdapter
import com.dicoding.submission2.data.UserBookRepository
import com.dicoding.submission2.data.local.datastore.SettingPreferences
import com.dicoding.submission2.databinding.ActivityMainBinding
import com.dicoding.submission2.data.remote.ApiConfig
import com.dicoding.submission2.model.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    val factory: RepoModelFactory = RepoModelFactory.getInstance(this)
    val viewModel: RepoViewModel by viewModels {
        factory
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        mainViewModel.search.observe(this) { search ->
            showRecyclerList(search)
        }
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }


        val pref = SettingPreferences.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(this, ViewModelFactory(pref))[SettingViewModel::class.java]
        settingViewModel.getThemeSettings().observe(this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                findUser(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showRecyclerList(item: List<ItemsItem>) {
        val layoutManager = LinearLayoutManager(this)
        binding.rvList.layoutManager = layoutManager
        val searchUserAdapter = SearchUserAdapter(item)
        searchUserAdapter.notifyDataSetChanged()
        binding.rvList.adapter = searchUserAdapter
        searchUserAdapter.setOnItemClickCallback(object : SearchUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                val user = ItemsItem(
                    data.login,
                    data.avatarUrl,
                    data.htmlUrl
                )
                val detailUserIntent = Intent(this@MainActivity, DetailUserActivity::class.java)
                detailUserIntent.putExtra(DetailUserActivity.EXTRA_USER, user.login)
                detailUserIntent.putExtra(DetailUserActivity.EXTRA_LINK, user.htmlUrl)
                detailUserIntent.putExtra(DetailUserActivity.EXTRA_IMG, user.avatarUrl)
                startActivity(detailUserIntent)
            }
        })
    }

    private fun findUser(query: String) {
        mainViewModel.mIsLoading.value = true

        viewModel.setBookmarkList(query)

        val client = ApiConfig.getApiService().searchUser(query)
        client.enqueue(object : Callback<GitSearchResponse> {
            override fun onResponse(
                call: Call<GitSearchResponse>,
                response: Response<GitSearchResponse>
            ) {
                mainViewModel.mIsLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        mainViewModel.mSearch.setValue(responseBody.items)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GitSearchResponse>, t: Throwable) {
                mainViewModel.mIsLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorite -> {
                val i = Intent(this, BookmarkActivity::class.java)
                  startActivity(i)
                true
            }
            R.id.setting -> {
                val i = Intent(this, SettingActivity::class.java)
                startActivity(i)
                // showAlertDialog()
               //  SettingActivity().show(supportFragmentManager, "MySettingFragment")
                true
            }
            else -> true
        }
    }
    fun showAlertDialog(): Boolean {


        MaterialAlertDialogBuilder(this)
            .setTitle("Title")
            .setMessage("Message")
            .setNegativeButton("CANCEL") { dialog, which ->
                // Respond to neutral button press
            }
            .setPositiveButton("OK") { dialog, which ->
                // Respond to positive button press
            }
            .show()
        return true
    }
    companion object {
        private const val TAG = "HomeActivity"
    }
}