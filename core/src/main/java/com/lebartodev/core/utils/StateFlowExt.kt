package com.lebartodev.core.utils

import com.lebartodev.lib_utils.utils.AsyncResult
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow

suspend fun <T> (suspend () -> T).loadIntoStateFlow(flow: MutableStateFlow<AsyncResult<T>>) {
    flow.value = AsyncResult.Loading()
    try {
        val result = invoke()
        flow.value = AsyncResult.Success(result)
    } catch (e: CancellationException) {
        @Suppress("RethrowCaughtException")
        throw e
    } catch (@Suppress("TooGenericExceptionCaught") e: Throwable) {
        flow.value = AsyncResult.Error(e)
    }
}