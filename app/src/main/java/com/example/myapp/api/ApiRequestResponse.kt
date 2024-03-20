package com.example.myapp.api

import android.util.Log
import com.example.myapp.App.Companion.gson
import com.example.myapp.utils.getData
import com.google.gson.JsonObject
import retrofit2.Response

abstract class ApiRequestResponse {

    suspend fun <T : Any?> apiRequest(call: suspend () -> Response<T>?): ApiResponse? {
        val response = call.invoke()

        Log.d("API>>", "REQUEST>>URL>> ${gson.toJson(response?.raw()?.request?.url?.toUrl())}")
        Log.d("API>>", "REQUEST>>PARAMETERS>> ${gson.toJson(response?.raw()?.request)}")
        Log.d("API>>", "REQUEST>>CODE>> ${gson.toJson(response?.code())}")
        Log.d("API>>", "REQUEST>>RESPONSE>> ${gson.toJson(response?.body())}")


        // Log.d("API>>", "REQUEST>>ERROR>> ${getData(response?.errorBody()?.string(), JsonObject::class.java)?.get("message")?.asString}")
        // PLEASE DON"T UNCOMMENT THIS CAUSES APP CRASH
        /**
         *
        FATAL EXCEPTION: main
        Process: com.tracks4hacks, PID: 15795
        com.google.gson.JsonSyntaxException: Expected a com.google.gson.JsonObject but was com.google.gson.JsonPrimitive; at path $
        at com.google.gson.internal.bind.TypeAdapters$34$1.read(TypeAdapters.java:1010)
        at com.google.gson.Gson.fromJson(Gson.java:1227)
        at com.google.gson.Gson.fromJson(Gson.java:1137)
        at com.google.gson.Gson.fromJson(Gson.java:1047)
        at com.google.gson.Gson.fromJson(Gson.java:982)
         * **/
        return response?.let {
            val jsonObject = getData(response.body(), JsonObject::class.java)
            val apiResponse = ApiResponse()
            apiResponse.statusCode = response.code()
            apiResponse.type = jsonObject?.get("type")?.asString

            if (response.isSuccessful) {
                apiResponse.normalData = jsonObject
                apiResponse.message = jsonObject?.get("message")?.asString
                apiResponse.features = jsonObject?.get("features")
                Log.d("API>>", "REQUEST>>RESPONSE>>API>>SUCCESS>> $apiResponse")
                apiResponse

            } else {
                val errorMsg = getData(response.errorBody()?.string(), JsonObject::class.java)?.get("message")?.asString
                if (errorMsg.isNullOrEmpty().not()) {
                    apiResponse.message = errorMsg
                } else {
                    apiResponse.message = response.raw().networkResponse?.message.toString()
                }
                Log.d("API>>", "REQUEST>>RESPONSE>>API>> $apiResponse")
                apiResponse
            }
        } ?: return null

    }

}