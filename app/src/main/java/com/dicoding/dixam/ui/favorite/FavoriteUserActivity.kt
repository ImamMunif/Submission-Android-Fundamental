package com.dicoding.dixam.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.dixam.R
import com.dicoding.dixam.databinding.ActivityFavoriteUserBinding
import com.dicoding.dixam.ui.ViewModelFactory
import com.dicoding.dixam.ui.main.UserListAdapter

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var adapter: UserListAdapter
    private lateinit var favoriteUserViewModel: FavoriteUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(true)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        favoriteUserViewModel = ViewModelProvider(this, factory)[FavoriteUserViewModel::class.java]

        favoriteUserViewModel.favoriteUserList.observe(this) {
            showLoading(false)
            if (it != null) {
                val layoutManager = LinearLayoutManager(this)
                binding.rvFavoriteUsers.layoutManager = layoutManager
                adapter = UserListAdapter(it)
                binding.rvFavoriteUsers.adapter = adapter
            } else {
                binding.rvFavoriteUsers.visibility = View.GONE
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.rvFavoriteUsers.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}