package com.lebartodev.lib.data.network

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    val id: Long,
    val adult: Boolean?,
    val budget: Int?,
    val genres: List<GenreResponse>? = null,
    val overview: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    val title: String,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("vote_count") val voteCount: Int?
)