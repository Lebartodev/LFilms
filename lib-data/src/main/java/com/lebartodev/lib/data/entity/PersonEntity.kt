package com.lebartodev.lib.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class PersonEntity(
    @PrimaryKey val id: Long,
    val profilePath: String?,
    val name: String?,
    val biography: String?,
    val birthday: Date,
    val deathday: Date?,
    val place_of_birth: String?
)
