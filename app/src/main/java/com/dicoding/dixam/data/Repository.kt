package com.dicoding.dixam.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.dixam.data.response.ItemsItem
import com.dicoding.dixam.data.retrofit.ApiService
import com.dicoding.dixam.database.FavoriteUserDAO

class Repository(
    private val apiService: ApiService,
    private val favoriteUserDao: FavoriteUserDAO,
) {
    fun findUsers(username: String): LiveData<Result<List<ItemsItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUsers(username)
            emit(Result.Success(response.items))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService,
            favoriteUserDao: FavoriteUserDAO,
        ): Repository = instance ?: synchronized(this) {
            instance ?: Repository(apiService, favoriteUserDao)
        }.also { instance = it }
    }
}