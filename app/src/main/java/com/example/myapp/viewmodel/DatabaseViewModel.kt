package com.example.myapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapp.api.repository.UserDbRepository
import com.example.myapp.room.MyDatabase
import com.example.myapp.room.UserData


class DatabaseViewModel(context:Context): ViewModel() {
    private val userRepository: UserDbRepository

    init {
        val database = MyDatabase.getInstance(context)
        val userDao = database.userDao()
        userRepository = UserDbRepository(userDao)
    }

    // USER
    fun getUser(): LiveData<List<UserData>>? {
        return userRepository.getUser()
    }

    fun insertUser(user: UserData) {
        userRepository.insertUser(user)
    }

}