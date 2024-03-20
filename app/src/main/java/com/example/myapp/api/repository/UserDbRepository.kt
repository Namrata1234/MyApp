package com.example.myapp.api.repository

import androidx.lifecycle.LiveData
import com.example.myapp.room.MyUserDao
import com.example.myapp.room.UserData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserDbRepository(private val userDao: MyUserDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun getUser(): LiveData<List<UserData>>? {
        return userDao.getUser()
    }

    fun insertUser(newUser: UserData) {
        coroutineScope.launch(Dispatchers.IO) {
            userDao.insertUser(user = newUser)
        }
    }

}