package com.dicoding.dixam.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.dixam.data.Repository
import com.dicoding.dixam.data.Result
import com.dicoding.dixam.data.response.UserDetailResponse

class UserDetailViewModel(private val repository: Repository) : ViewModel() {

    private val _userDetail = MediatorLiveData<Result<UserDetailResponse>>()
    val userDetail: LiveData<Result<UserDetailResponse>> = _userDetail

    fun getUserDetail(username: String) {
        val liveData = repository.getUserDetail(username)
        _userDetail.addSource(liveData) { result ->
            _userDetail.value = result
        }
    }
}