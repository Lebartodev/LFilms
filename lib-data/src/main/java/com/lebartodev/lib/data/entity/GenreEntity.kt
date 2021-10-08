package com.lebartodev.lib.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lebartodev.lib.data.network.GenreResponse
import com.lebartodev.lib.data.network.GenresResponse

@Entity
data class GenreEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String
)

fun GenreResponse.toGenreEntity() = GenreEntity(id, name)

fun GenresResponse.toGenreEntities() = genres.map { it.toGenreEntity() }
