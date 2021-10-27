package com.lebartodev.lib_authentication.network

import com.lebartodev.lib_authentication.network.request.SessionIdRequest
import com.lebartodev.lib_authentication.network.response.AccountResponse
import com.lebartodev.lib_authentication.network.response.RequestTokenResponse
import com.lebartodev.lib_authentication.network.response.SessionIdResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AccountService {
    @GET("account")
    suspend fun getDetails(@Query("session_id") sessionId: String): AccountResponse

    @GET("authentication/token/new")
    suspend fun generateRequestToken(): RequestTokenResponse

    @POST("authentication/session/new")
    suspend fun generateSession(@Body request: SessionIdRequest): SessionIdResponse

//    @POST("authentication/session/new")
//    suspend fun generateSessionId(): RequestTokenResponse


}