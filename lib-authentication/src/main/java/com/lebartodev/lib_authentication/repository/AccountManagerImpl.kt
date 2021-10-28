package com.lebartodev.lib_authentication.repository

import android.util.Log
import com.lebartodev.lib_authentication.db.dao.AccountDao
import com.lebartodev.lib_authentication.db.entity.AccountEntity
import com.lebartodev.lib_authentication.mapper.toEntity
import com.lebartodev.lib_authentication.network.AccountService
import com.lebartodev.lib_authentication.network.request.SessionIdRequest
import com.lebartodev.lib_utils.di.scope.AppScope
import com.lebartodev.lib_utils.utils.AppCoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@AppScope
class AccountManagerImpl @Inject constructor(
    private val accountDao: AccountDao,
    private val accountService: AccountService,
    private val appCoroutineScope: AppCoroutineScope
) : AccountManager {
    private val accountMutableState = MutableStateFlow<AccountEntity?>(null)
    private val requestTokenState = MutableStateFlow<String?>("sss")


    init {
        Log.d("AccountManager", "init $this")
        appCoroutineScope.launch {
            accountMutableState.value = accountDao.getAccount()
            if (!isAuthorized()) {
                generateRequestToken()
            }
        }
    }

    override fun account(): StateFlow<AccountEntity?> {
        return accountMutableState
    }

    override fun requestToken(): StateFlow<String?> {
        return requestTokenState
    }

    override suspend fun isAuthorized(): Boolean {
        return accountDao.getAccount() != null
    }

    private suspend fun generateRequestToken() {
        val requestToken = accountService.generateRequestToken().requestToken
        requestTokenState.value = requestToken
    }

    override fun updateSessionId() {
        appCoroutineScope.launch {
            try {
                val requestBody = SessionIdRequest(requestTokenState.value ?: return@launch)
                val sessionIdResponse = accountService.generateSession(requestBody)
                val account = accountService.getDetails(sessionIdResponse.sessionId)
                accountDao.upsertAccount(account.toEntity(sessionId = sessionIdResponse.sessionId))
            } catch (e: Exception) {
                Log.e("AccountManager", "updateSessionId", e)
            }
        }
    }
}