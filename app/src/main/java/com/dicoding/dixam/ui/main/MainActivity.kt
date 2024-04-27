package com.dicoding.dixam.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.dixam.R
import com.dicoding.dixam.data.Result
import com.dicoding.dixam.databinding.ActivityMainBinding
import com.dicoding.dixam.ui.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserListAdapter
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        userViewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]

        setRecyclerViewData()

        findUsersList(getString(R.string.initial_query_username))
    }

    private fun findUsersList(username: String) {
        userViewModel.setUsername(username).observe(this) {
            when (it) {
                is Result.Loading -> showLoading(true)
                is Result.Error -> {
                    showLoading(false)
                }
                is Result.Success -> {
                    showLoading(false)
                    adapter = UserListAdapter((it.data))
                    binding.rvUsersList.adapter = adapter
                }
            }
        }
    }

    private fun setRecyclerViewData() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvUsersList.layoutManager = layoutManager

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.setText(searchView.text)
                    findUsersList(searchBar.text.toString())
                    searchView.hide()
                    false
                }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.rvUsersList.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}