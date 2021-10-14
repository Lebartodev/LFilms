package com.lebartodev.lib.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GenreEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String
)