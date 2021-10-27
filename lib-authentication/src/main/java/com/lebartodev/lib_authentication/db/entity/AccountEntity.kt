package com.lebartodev.lib_authentication.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AccountEntity(
    @PrimaryKey val id: Long,
    val name: String?,
    val userName: String?,
    val sessionId: String?,
)