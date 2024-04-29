package com.dicoding.dixam.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.dixam.database.FavoriteUserDAO
import com.dicoding.dixam.data.response.UserDetailResponse
import com.dicoding.dixam.data.response.ItemsItem
import com.dicoding.dixam.data.retrofit.ApiService
import com.dicoding.dixam.database.FavoriteUser
import kotlinx.coroutines.Dispatchers

class Repository(
    private val apiService: ApiService,
    private val favoriteUserDao: FavoriteUserDAO,
) {
    fun findUsers(username: String): LiveData<Result<List<ItemsItem>>> =
        liveData(Dispatchers.IO) {
            emit(Result.Loading)
            try {
                val response = apiService.getUsers(username)
                Log.d("Debug1findUser", response.toString())
                emit(Result.Success(response.items))

            } catch (e: Exception) {
                Log.d("Debug2findUser", e.message.toString())
                emit(Result.Error(e.message.toString()))
            }
        }

    fun getUserDetail(username: String): LiveData<Result<UserDetailResponse>> =
        liveData(Dispatchers.IO) {
            emit(Result.Loading)
            try {
                val response = apiService.getUserDetail(username)
                Log.d("Debug3getUserDetail", response.toString())
                emit(Result.Success(response))
            } catch (e: Exception) {
                Log.d("Debug4getUserDetail", e.message.toString())
                emit(Result.Error(e.message.toString()))
            }
        }

    fun getFollowerUsers(username: String): LiveData<Result<List<ItemsItem>>> =
        liveData(Dispatchers.IO) {
            emit(Result.Loading)
            try {
                val response = apiService.getFollowers(username)
                Log.d("Debug5getUserFollower", response.toString())
                emit(Result.Success(response))
            } catch (e: Exception) {
                Log.d("Debug6getUserFollower", e.message.toString())
                emit(Result.Error(e.message.toString()))
            }
        }

    fun getFollowingUsers(username: String): LiveData<Result<List<ItemsItem>>> =
        liveData(Dispatchers.IO) {
            emit(Result.Loading)
            try {
                val response = apiService.getFollowings(username)
                Log.d("Debug7getUserFollowing", response.toString())
                emit(Result.Success(response))
            } catch (e: Exception) {
                Log.d("Debug8getUserFollowing", e.message.toString())
                emit(Result.Error(e.message.toString()))
            }
        }

    fun isFavorited(username: String): LiveData<Boolean> = favoriteUserDao.isFavorited(username)

    fun getFavoriteUsers():LiveData<List<FavoriteUser>> = favoriteUserDao.getFavoriteUsers()

    suspend fun addFavorite(favorite: FavoriteUser) {
        favoriteUserDao.insert(favorite)
    }

    suspend fun removeFavorite(favorite: FavoriteUser) {
        favoriteUserDao.delete(favorite)
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
