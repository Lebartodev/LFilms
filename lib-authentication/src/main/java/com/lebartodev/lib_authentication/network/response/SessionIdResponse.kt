package com.lebartodev.lib_authentication.network.response

import com.google.gson.annotations.SerializedName

data class SessionIdResponse(
    val success: Boolean,
    @SerializedName("session_id") val sessionId: String
)
