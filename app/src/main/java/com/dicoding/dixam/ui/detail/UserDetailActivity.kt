package com.dicoding.dixam.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.dicoding.dixam.R
import com.dicoding.dixam.databinding.ActivityUserDetailBinding
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding
    private val userDetailViewModel by viewModels<UserDetailViewModel>()
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUserData()
    }

    private fun setUserData() {
        username = intent.getStringExtra("username") ?: "DefaultUsername"

        userDetailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        userDetailViewModel.getUserDetail(username)

        userDetailViewModel.userDetail.observe(this) { userDetail ->
            Glide
                .with(this)
                .load(userDetail.avatarUrl)
                .fitCenter()
                .into(binding.imgDetailItemAvatar)

            binding.tvDetailUsername.text = userDetail.login
            binding.tvDetailFullname.text = userDetail.name

            val followersText = getString(R.string.followers, userDetail.followers.toString())
            val followingText = getString(R.string.following, userDetail.following.toString())

            binding.tvFollowers.text = followersText
            binding.tvFollowing.text = followingText
        }
    }

    private fun showLoading(isLoading: Boolean) {
        with(binding){
            tvDetailUsername.visibility = if (isLoading) View.GONE else View.VISIBLE
            tvDetailFullname.visibility = if (isLoading) View.GONE else View.VISIBLE
            tvFollowers.visibility = if (isLoading) View.GONE else View.VISIBLE
            tvFollowing.visibility = if (isLoading) View.GONE else View.VISIBLE
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}