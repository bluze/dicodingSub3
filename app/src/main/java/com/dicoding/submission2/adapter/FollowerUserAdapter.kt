package com.dicoding.submission2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submission2.data.remote.GithubUser
import com.dicoding.submission2.databinding.ItemRowFollowerBinding

class FollowerUserAdapter(private val listFollowers: List<GithubUser>) :
    RecyclerView.Adapter<FollowerUserAdapter.ListViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding =
            ItemRowFollowerBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listFollowers[position]
        Glide.with(holder.itemView.context)
            .load(data.avatarUrl)
            .circleCrop()
            .into(holder.binding.imgUserPhoto)

        holder.binding.tvUserName.text = data.login
        holder.binding.tvLink.text = data.htmlUrl
    }

    override fun getItemCount(): Int = listFollowers.size

    class ListViewHolder(val binding: ItemRowFollowerBinding) : RecyclerView.ViewHolder(binding.root)
}