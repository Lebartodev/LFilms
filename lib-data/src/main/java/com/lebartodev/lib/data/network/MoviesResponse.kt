package com.lebartodev.lib.data.network

data class MoviesResponse(
    val page: Int,
    val total_pages: Int,
    val results: List<MovieResponse>? = arrayListOf()
)