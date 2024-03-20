package com.example.myapp.api

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.example.myapp.App.Companion.gson
import com.example.myapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient(context: Context) {
    
    private val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val httpClient: OkHttpClient by lazy {
        OkHttpClient
            .Builder()
            .connectTimeout(2, TimeUnit.MINUTES).readTimeout(2, TimeUnit.MINUTES).writeTimeout(2, TimeUnit.MINUTES)
            .addInterceptor(loggingInterceptor)
            .build()
    }
    val retrofit: Retrofit by lazy {
        Retrofit
            .Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }


}