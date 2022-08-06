package com.dicoding.submission2.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.submission2.R
import com.dicoding.submission2.data.local.entity.UserBookEntity
import com.dicoding.submission2.data.remote.ItemsItem
import com.dicoding.submission2.databinding.ItemRowUserBinding

class UserAdapter(private val onBookmarkClick: (UserBookEntity) -> Unit) : ListAdapter<UserBookEntity, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)

        val ivBookmark = holder.binding.ivBookmark
        if (user.isBookmarked) {
            ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.ic_baseline_favorite_full24))
        } else {
            ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.ic_baseline_favorite_border_24))
        }
        ivBookmark.setOnClickListener {
            onBookmarkClick(user)
        }
    }

    class MyViewHolder(val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(user: UserBookEntity) {
            binding.tvUserName.text = user.login
            binding.tvLink.text = user.htmlUrl
            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .circleCrop()
                .into(binding.imgUserPhoto)
          //  itemView.setOnClickListener {
          //      onItemClickCallback.onItemClicked(user[adapterPosition])
          //  }

        }
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: ItemsItem)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<UserBookEntity> =
            object : DiffUtil.ItemCallback<UserBookEntity>() {
                override fun areItemsTheSame(oldUser: UserBookEntity, newUser: UserBookEntity): Boolean {
                    return oldUser.login == newUser.login
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldUser: UserBookEntity, newUser: UserBookEntity): Boolean {
                    return oldUser == newUser
                }
            }
    }
}