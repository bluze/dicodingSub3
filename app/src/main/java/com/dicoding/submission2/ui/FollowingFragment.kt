package com.dicoding.submission2.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission2.data.remote.GithubUser
import com.dicoding.submission2.adapter.FollowerUserAdapter
import com.dicoding.submission2.databinding.FragmentFollowingBinding
import com.dicoding.submission2.model.MainViewModel
import com.dicoding.submission2.data.remote.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingFragment : Fragment() {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by viewModels()
    private val tipe = "following"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (mainViewModel.mFollowers.value == null) {
            findFollowing()
        }

        mainViewModel.following.observe(viewLifecycleOwner) { following ->
            showRecyclerList(following)
        }
        mainViewModel.isLoading1.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun findFollowing() {
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
                        mainViewModel.mFollowing.value = responseBody
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
        binding.rvFollowing.layoutManager = layoutManager
        val followerUserAdapter = FollowerUserAdapter(item)
        followerUserAdapter.notifyDataSetChanged()
        binding.rvFollowing.adapter = followerUserAdapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar1.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        private const val TAG = "FollowerFragment"
    }
}