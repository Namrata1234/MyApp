package com.example.myapp.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface MyUserDao {

    @Query("SELECT * FROM UserTable")
    fun getUser(): LiveData<List<UserData>>?

    @Insert
    fun insertUser(user: UserData)
    }
