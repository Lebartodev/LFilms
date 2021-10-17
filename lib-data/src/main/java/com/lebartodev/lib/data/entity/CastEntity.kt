package com.lebartodev.lib.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movieId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CastEntity(
    @PrimaryKey val id: Long,
    val movieId: Long,
    val name: String?,
    val originalName: String?,
    val castId: Long?,
    val creditId: String?,
    val character: String?,
    val profilePath: String?,
    val order: Int?
)