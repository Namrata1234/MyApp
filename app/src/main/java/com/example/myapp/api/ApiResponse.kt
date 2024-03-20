package com.example.myapp.api

import java.io.Serializable

data class ApiResponse(
    var statusCode: Int? = null,
    var type: String? = null,
    var message: String? = null,
    var normalData: Any? = null,
    var features: Any? = null
) : Serializable