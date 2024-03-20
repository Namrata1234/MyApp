package com.example.myapp.model

import androidx.room.Entity
import java.io.Serializable

data class UserList(
    val id: String? = null,
    val email: String? = null,
    val first_name: String? = null,
    val last_name: String? = null,
    val avatar: String? = null

) : Serializable