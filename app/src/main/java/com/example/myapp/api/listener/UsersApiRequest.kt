package com.example.myapp.api.listener

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface UsersApiRequest {

    //CATEGORY
    @GET("users")
    suspend fun apiCallForUsers(@QueryMap parameters: HashMap<String, Any?>): Response<Any?>?

}