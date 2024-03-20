package com.example.myapp.api.repository

import com.example.myapp.api.ApiRequestResponse
import com.example.myapp.api.listener.UsersApiRequest

class UsersRepository(private val apiRequest: UsersApiRequest) : ApiRequestResponse() {

    suspend fun apiCallForUsers(page: Int?) = apiRequest {
        val parameters: HashMap<String, Any?> = hashMapOf()
        if (page!= null && page > 0 ) {
            parameters["page"] = page
        }

        apiRequest.apiCallForUsers(parameters)
    }

}