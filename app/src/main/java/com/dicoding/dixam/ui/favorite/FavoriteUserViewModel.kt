package com.dicoding.dixam.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.dixam.data.Repository
import com.dicoding.dixam.data.response.ItemsItem

class FavoriteUserViewModel(private val repository: Repository): ViewModel() {
    private val _favoriteUserList = MediatorLiveData<List<ItemsItem>>()
    val favoriteUserList: LiveData<List<ItemsItem>> = _favoriteUserList

    private fun getFavoriteUsers() {
        val liveData = repository.getFavoriteUsers()
        _favoriteUserList.addSource(liveData) { favoriteUserList ->
            // Convert FavoriteUser objects to ItemsItem objects
            val convertedList = favoriteUserList.map { favoriteUser ->
                ItemsItem(favoriteUser.username, favoriteUser.avatarUrl ?: "")
            }
            _favoriteUserList.value = convertedList
        }
    }

    init {
        getFavoriteUsers()
    }
}