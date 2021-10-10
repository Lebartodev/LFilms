package com.lebartodev.lib.data.network

data class MovieResponse(
    val id: Long,
    val adult: Boolean?,
    val budget: Int?,
    val genres: List<GenreResponse>? = null,
    val overview: String?,
    val poster_path: String?,
    val release_date: String?,
    val title: String,
    val vote_average: Double,
    val vote_count: Int?
)