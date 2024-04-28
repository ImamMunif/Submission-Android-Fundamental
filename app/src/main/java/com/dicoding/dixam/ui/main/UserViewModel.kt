package com.dicoding.dixam.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.dixam.data.Repository
import com.dicoding.dixam.data.Result
import com.dicoding.dixam.data.response.ItemsItem

class UserViewModel(private val repository: Repository) : ViewModel() {

    private val _userList = MediatorLiveData<Result<List<ItemsItem>>>()
    val userList: LiveData<Result<List<ItemsItem>>> = _userList

    init {
        findUsers("arif")
    }

    fun findUsers(username: String) {
        val liveData = repository.findUsers(username)
        _userList.addSource(liveData) { result ->
            _userList.value = result
        }
    }
}