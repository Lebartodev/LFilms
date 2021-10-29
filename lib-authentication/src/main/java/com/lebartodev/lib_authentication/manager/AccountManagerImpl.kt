package com.lebartodev.lib_authentication.manager

import android.util.Log
import com.lebartodev.lib_authentication.db.dao.AccountDao
import com.lebartodev.lib_authentication.db.entity.AccountEntity
import com.lebartodev.lib_authentication.mapper.toEntity
import com.lebartodev.lib_authentication.network.AccountService
import com.lebartodev.lib_authentication.network.request.SessionIdRequest
import com.lebartodev.lib_utils.di.scope.AppScope
import com.lebartodev.lib_utils.utils.AppCoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AppScope
class AccountManagerImpl @Inject constructor(
    private val accountDao: AccountDao,
    private val accountService: AccountService,
    private val appCoroutineScope: AppCoroutineScope
) : AccountManager {
    private val accountMutableState = MutableStateFlow<AccountEntity?>(null)
    private val requestTokenState = MutableStateFlow<String?>(null)

    init {
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
        return accountMutableState.value != null
    }

    private suspend fun generateRequestToken() {
        val response = accountService.generateRequestToken()
        requestTokenState.value = response.requestToken
        Log.d(TAG, "generateRequestToken: ${response.requestToken}")
        scheduleRefreshRequestToken(response.expiresAt)
    }

    private fun scheduleRefreshRequestToken(expiresAt: Date) {
        val currentTime = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        val timeUntilExpires = expiresAt.time - currentTime.time.time
        appCoroutineScope.launch {
            Log.d(TAG, "Schedule refresh for $timeUntilExpires millis")
            delay(timeUntilExpires)
            generateRequestToken()
        }
    }

    @SuppressWarnings("TooGenericExceptionCaught")
    override fun updateSessionId() {
        appCoroutineScope.launch {
            try {
                val requestBody = SessionIdRequest(requestTokenState.value ?: return@launch)
                val sessionIdResponse = accountService.generateSession(requestBody)
                val account = accountService.getDetails(sessionIdResponse.sessionId)
                accountDao.upsertAccount(account.toEntity(sessionId = sessionIdResponse.sessionId))
            } catch (e: Throwable) {
                Log.e(TAG, "updateSessionId", e)
            }
        }
    }

    companion object {
        const val TAG = "AccountManager"
    }
}