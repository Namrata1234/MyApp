package com.example.myapp.model

import java.io.Serializable

data class PageInfo(
    val page: Int? = null,
    val per_page: Int? = null,
    val total: Int? = null,
    val total_pages: Int? = null
) : Serializable