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
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MovieEntity

        if (id != other.id) return false
        if (title != other.title) return false
        if (posterPath != other.posterPath) return false
        if (overview != other.overview) return false
        if (voteAverage != other.voteAverage) return false
        if (adult != other.adult) return false
        if (budget != other.budget) return false
        if (voteCount != other.voteCount) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + (posterPath?.hashCode() ?: 0)
        result = 31 * result + (overview?.hashCode() ?: 0)
        result = 31 * result + voteAverage.hashCode()
        result = 31 * result + (adult?.hashCode() ?: 0)
        result = 31 * result + (budget ?: 0)
        result = 31 * result + (voteCount ?: 0)
        return result
    }
}