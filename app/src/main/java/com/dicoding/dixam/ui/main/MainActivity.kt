package com.dicoding.dixam.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.dixam.R
import com.dicoding.dixam.data.Result
import com.dicoding.dixam.data.SettingPreferences
import com.dicoding.dixam.data.dataStore
import com.dicoding.dixam.databinding.ActivityMainBinding
import com.dicoding.dixam.ui.ViewModelFactory
import com.dicoding.dixam.ui.favorite.FavoriteActivity
import com.dicoding.dixam.ui.settings.SettingsActivity
import com.dicoding.dixam.ui.settings.SettingsViewModel
import com.example.github_user_app.util.SettingsViewModelFactory

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

        setTheme()

        setRecyclerViewData()

        setMainMenu()

        userViewModel.userList.observe(this) {
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

                else -> {}
            }
        }
    }

    private fun setTheme() {
        val preference = SettingPreferences.getInstance(application.dataStore)

        val settingsViewModel = ViewModelProvider(
            this,
            SettingsViewModelFactory(preference)
        )[SettingsViewModel::class.java]

        settingsViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun setMainMenu(){
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_settings -> {
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menu_favorite -> {
                    val intent = Intent(this, FavoriteActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
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
                    searchView.hide()
                    userViewModel.findUsers(searchBar.text.toString())
                    false
                }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.rvUsersList.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}