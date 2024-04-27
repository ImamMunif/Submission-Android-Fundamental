package com.dicoding.dixam.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.dixam.data.Repository
import com.dicoding.dixam.data.Result
import com.dicoding.dixam.data.response.ItemsItem

class UserViewModel(private val repository: Repository) : ViewModel() {

    //    fun setUsername(username: String): LiveData<Result<List<ItemsItem>>> = repository.findUsers(username)

    fun setUsername(username: String): LiveData<Result<List<ItemsItem>>> {
        Log.d("Debug2", username)
        return repository.findUsers(username)
    }

}