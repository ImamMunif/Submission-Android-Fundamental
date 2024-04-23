package com.dicoding.dixam.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.dixam.data.response.ItemsItem
import com.dicoding.dixam.databinding.ItemLayoutBinding

class UserListAdapter(private val githubUserList: List<ItemsItem>) :
    RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    inner class UserViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(githubUser: ItemsItem) {
            binding.tvItemName.text = githubUser.login

            Glide
                .with(itemView.context)
                .load(githubUser.avatarUrl)
                .fitCenter()
                .into(binding.imgItemAvatar)

//            binding.itemLayout.setOnClickListener {
//                val intent = Intent(itemView.context, DetailUserActivity::class.java)
//                intent.putExtra("username", githubUser.login)
//                itemView.context.startActivity(intent)
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return githubUserList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val githubUser = githubUserList[position]
        holder.bind(githubUser)
    }
}