package com.lebartodev.lib.data.network

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    val page: Int,
    @SerializedName("total_pages") val totalPages: Int,
    val results: List<MovieResponse>? = arrayListOf()
)