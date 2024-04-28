package com.dicoding.dixam.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.dixam.R
import com.dicoding.dixam.data.response.UserDetailResponse
import com.dicoding.dixam.data.Result
import com.dicoding.dixam.databinding.ActivityUserDetailBinding
import com.dicoding.dixam.ui.ViewModelFactory
import com.dicoding.dixam.ui.follow.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var username: String
    private lateinit var userDetailViewModel: UserDetailViewModel

    companion object {
        private val TAB_TITLES = listOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        userDetailViewModel = ViewModelProvider(this, factory)[UserDetailViewModel::class.java]

        username = intent.getStringExtra("username") ?: "DefaultUsername"

        userDetailViewModel.getUserDetail(username)

        userDetailViewModel.userDetail.observe(this) {
            when (it) {
                is Result.Loading -> showLoading(true)
                is Result.Error -> {
                    showLoading(false)
                }

                is Result.Success -> {
                    showLoading(false)
                    setUserData(it.data)
                }
            }
        }

        setViewPager()
    }

    private fun setUserData(userDetail: UserDetailResponse) {
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

    private fun setViewPager() {
        val adapter = SectionsPagerAdapter(this, username)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            val tabText = this.getString(TAB_TITLES[position])
            tab.text = tabText
        }.attach()
    }

    private fun showLoading(isLoading: Boolean) {
        with(binding) {
            tvDetailUsername.visibility = if (isLoading) View.GONE else View.VISIBLE
            tvDetailFullname.visibility = if (isLoading) View.GONE else View.VISIBLE
            tvFollowers.visibility = if (isLoading) View.GONE else View.VISIBLE
            tvFollowing.visibility = if (isLoading) View.GONE else View.VISIBLE
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}