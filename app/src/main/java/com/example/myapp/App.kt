package com.example.myapp

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class App :Application() {

    companion object{
        val gson: Gson
            get() {
                return GsonBuilder().create()
            }


    }
}