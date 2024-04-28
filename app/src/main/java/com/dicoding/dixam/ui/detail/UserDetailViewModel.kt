package com.dicoding.dixam.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.dixam.data.Repository
import com.dicoding.dixam.data.Result
import com.dicoding.dixam.data.response.UserDetailResponse
import com.dicoding.dixam.database.FavoriteUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserDetailViewModel(private val repository: Repository) : ViewModel() {

    private val _userDetail = MediatorLiveData<Result<UserDetailResponse>>()
    val userDetail: LiveData<Result<UserDetailResponse>> = _userDetail

    private val _isFavorited = MediatorLiveData<Boolean>()
    val isFavorited: LiveData<Boolean> = _isFavorited

    fun getUserDetail(username: String) {
        val liveData = repository.getUserDetail(username)
        _userDetail.addSource(liveData) { result ->
            _userDetail.value = result
        }
    }

    fun isFavorited(username: String) {
        val liveData = repository.isFavorited(username)
        _isFavorited.addSource(liveData) { result ->
            _isFavorited.value = result
        }
    }

    fun addFavorite(favorite: FavoriteUser) =
        viewModelScope.launch(Dispatchers.IO) { repository.addFavorite(favorite) }

    fun removeFavorite(favorite: FavoriteUser) =
        viewModelScope.launch(Dispatchers.IO) { repository.removeFavorite(favorite) }
}