package com.lebartodev.lib.data.network

data class GenreResponse(
    val id: Long,
    val name: String
)

data class GenresResponse(
    val genres: List<GenreResponse>
)