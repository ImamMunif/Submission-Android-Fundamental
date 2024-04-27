package com.dicoding.dixam.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.dixam.data.Repository
import com.dicoding.dixam.di.Injection
import com.dicoding.dixam.ui.main.UserViewModel

class ViewModelFactory private constructor(private val repository: Repository): ViewModelProvider.NewInstanceFactory(){

    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory = instance?: synchronized(this) {
            instance ?: ViewModelFactory(Injection.provideRepository(context))
        }.also { instance = it }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(UserViewModel::class.java) -> return UserViewModel(repository) as T
            // ...
            // Others view model
            // ...
        }
        throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)
    }
}