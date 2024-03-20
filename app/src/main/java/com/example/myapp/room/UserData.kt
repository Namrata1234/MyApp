package com.example.myapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserTable")
data class UserData(
    @PrimaryKey var id:Int?=null,
    @ColumnInfo(name = "first_name") var first_name: String?=null,
    @ColumnInfo(name = "last_name") var last_name: String?=null,
    @ColumnInfo(name = "email") var email:String?=null,
    @ColumnInfo(name = "avatar") var avatar: String?=null

)
