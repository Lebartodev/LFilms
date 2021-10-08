package com.lebartodev.lib.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val posterPath: String?,
    val overview: String?,
    val voteAverage: Double,
    val adult: Boolean?,
    val budget: Int?,
    val voteCount: Int?

)

//val id: Long,
//val adult: Boolean,
//val budget: Int,
//val genres: List<GenreResponse>,
//val overview: String?,
//val poster_path: String?,
//val release_date: String?,
//val title: String,
//val vote_average: Float,
//val vote_count: Int