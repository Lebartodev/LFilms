package com.lebartodev.lib.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class MovieEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val posterPath: String?,
    val overview: String?,
    val voteAverage: Double,
    val adult: Boolean?,
    val budget: Int?,
    val voteCount: Int?
)