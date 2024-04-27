package com.dicoding.dixam.di

import android.content.Context
import com.dicoding.dixam.data.Repository
import com.dicoding.dixam.data.retrofit.ApiConfig
import com.dicoding.dixam.database.FavoriteUserDatabase

object Injection {
    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteUserDatabase.getDatabase(context)
        val dao = database.favoriteUserDao()
        return Repository.getInstance(apiService, dao)
    }
}