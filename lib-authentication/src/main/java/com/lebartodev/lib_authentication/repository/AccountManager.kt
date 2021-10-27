package com.lebartodev.lib_authentication.repository

import com.lebartodev.lib_authentication.db.entity.AccountEntity
import kotlinx.coroutines.flow.Flow

interface AccountManager {
    fun account(): Flow<AccountEntity>
    fun requestToken(): Flow<String?>
    suspend fun isAuthorized(): Boolean
    fun updateSessionId()
}