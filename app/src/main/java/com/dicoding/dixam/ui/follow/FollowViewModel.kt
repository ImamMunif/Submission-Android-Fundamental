package com.dicoding.dixam.ui.follow


import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.dixam.data.Repository
import com.dicoding.dixam.data.Result
import com.dicoding.dixam.data.response.ItemsItem


class FollowViewModel(private val repository: Repository) : ViewModel() {

    private val _followerList = MediatorLiveData<Result<List<ItemsItem>>>()
    val followerList: LiveData<Result<List<ItemsItem>>> = _followerList

    private val _followingList = MediatorLiveData<Result<List<ItemsItem>>>()
    val followingList: LiveData<Result<List<ItemsItem>>> = _followingList

    fun getFollowerList(username: String) {
        val liveData = repository.getFollowerUser(username)
        _followerList.addSource(liveData) { result ->
            _followerList.value = result
        }
    }

    fun getFollowingList(username: String) {
        val liveData = repository.getFollowingUser(username)
        _followingList.addSource(liveData) { result ->
            _followingList.value = result
        }
    }

//    private val _followList = MutableLiveData<List<ItemsItem>>()
//    val followList: LiveData<List<ItemsItem>> = _followList
//
//    private val _isLoading = MutableLiveData<Boolean>()
//    val isLoading: LiveData<Boolean> = _isLoading
//
//    fun getFollowList(username: String, tab: String) {
//        _isLoading.value = true
//        viewModelScope.launch(Dispatchers.IO) {
//            val client: Call<List<ItemsItem>> = if (tab == "follower") {
//                ApiConfig.getApiService().getFollowers(username)
//            } else {
//                ApiConfig.getApiService().getFollowings(username)
//            }
//            client.enqueue(object : Callback<List<ItemsItem>> {
//                override fun onResponse(
//                    call: Call<List<ItemsItem>>,
//                    response: Response<List<ItemsItem>>
//                ) {
//                    _isLoading.value = false
//                    if (response.isSuccessful) {
//                        _followList.value = response.body()
//                    } else {
//                        Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
//                    }
//                }
//
//                override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
//                    _isLoading.value = false
//                    Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
//                }
//            })
//        }
//    }

}