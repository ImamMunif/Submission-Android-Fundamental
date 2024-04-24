package com.dicoding.dixam.data.retrofit

import com.dicoding.dixam.data.response.UserDetailResponse
import com.dicoding.dixam.data.response.GithubResponse
import com.dicoding.dixam.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    fun getUser(@Query("q") login: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<UserDetailResponse>
}