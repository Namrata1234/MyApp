package com.example.myapp.model

import android.graphics.pdf.PdfDocument
import androidx.room.Entity
import java.io.Serializable

data class UserData(
    val data : ArrayList<UserList>? = null,
    var pageInfo :PageInfo? = null
) : Serializable