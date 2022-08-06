package com.dicoding.submission2.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission2.data.remote.GithubUser
import com.dicoding.submission2.adapter.FollowerUserAdapter
import com.dicoding.submission2.databinding.FragmentFollowerBinding
import com.dicoding.submission2.model.MainViewModel
import com.dicoding.submission2.data.remote.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FollowerFragment : Fragment() {
    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by viewModels()
    private val tipe = "followers"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (mainViewModel.mFollowers.value == null) {
            findFollowers()
        }

        mainViewModel.followers.observe(viewLifecycleOwner) { followers ->
            showRecyclerList(followers)
        }
        mainViewModel.isLoading1.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun findFollowers() {
        mainViewModel.mIsLoading1.value = true
        val userID = requireActivity().intent.getStringExtra(DetailUserActivity.EXTRA_USER)
        val client = ApiConfig.getApiService().getFollow(userID,tipe)
        client.enqueue(object : Callback<List<GithubUser>> {
            override fun onResponse(
                call: Call<List<GithubUser>>,
                response: Response<List<GithubUser>>
            ) {
                mainViewModel.mIsLoading1.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        mainViewModel.mFollowers.value = responseBody
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<GithubUser>>, t: Throwable) {
                mainViewModel.mIsLoading1.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    private fun showRecyclerList(item: List<GithubUser>) {
        val layoutManager = LinearLayoutManager(activity)
        binding.rvFollower.layoutManager = layoutManager
        val followerUserAdapter = FollowerUserAdapter(item)
        followerUserAdapter.notifyDataSetChanged()
        binding.rvFollower.adapter = followerUserAdapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar1.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        private const val TAG = "FollowerFragment"
    }
}