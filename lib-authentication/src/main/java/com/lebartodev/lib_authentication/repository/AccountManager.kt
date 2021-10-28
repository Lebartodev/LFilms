package com.lebartodev.lib_authentication.repository

import com.lebartodev.lib_authentication.db.entity.AccountEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface AccountManager {
    fun account(): Flow<AccountEntity?>
    fun requestToken(): StateFlow<String?>
    suspend fun isAuthorized(): Boolean
    fun updateSessionId()
}