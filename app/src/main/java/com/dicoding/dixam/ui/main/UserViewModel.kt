package com.dicoding.dixam.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.dixam.data.Repository
import com.dicoding.dixam.data.Result
import com.dicoding.dixam.data.response.ItemsItem

class UserViewModel (private val repository: Repository) : ViewModel() {

    fun setUsername(username: String): LiveData<Result<List<ItemsItem>>> = repository.findUsers(username)

}