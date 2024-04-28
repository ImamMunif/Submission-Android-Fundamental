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
    suspend fun getUsers(@Query("q") login: String
    ): GithubResponse

    @GET("users/{username}")
    suspend fun getUserDetail(
        @Path("username") username: String
    ): UserDetailResponse

    @GET("users/{username}/followers")
    suspend fun getFollowers(
        @Path("username") username: String
    ): List<ItemsItem>

    @GET("users/{username}/following")
    suspend fun getFollowings(
        @Path("username") username: String
    ): List<ItemsItem>

}