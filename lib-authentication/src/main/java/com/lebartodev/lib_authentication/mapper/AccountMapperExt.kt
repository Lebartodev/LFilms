package com.lebartodev.lib_authentication.mapper

import com.lebartodev.lib_authentication.db.entity.AccountEntity
import com.lebartodev.lib_authentication.network.response.AccountResponse

fun AccountResponse.toEntity(sessionId: String): AccountEntity {
    return AccountEntity(id, name, userName, sessionId)
}