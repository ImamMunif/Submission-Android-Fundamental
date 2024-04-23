package com.dicoding.dixam.data.retrofit

import com.dicoding.dixam.data.response.GithubResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    fun getUser(@Query("q") login: String): Call<GithubResponse>

}