package com.lebartodev.core.network

sealed class AsyncResult<T>(open val data: T? = null) {
    class Success<T>(data: T) : AsyncResult<T>(data) {
        override val data: T = super.data!!
    }

    class Error<T>(val error: Throwable?) : AsyncResult<T>()
    class Loading<T>(data: T? = null) : AsyncResult<T>(data)
}
