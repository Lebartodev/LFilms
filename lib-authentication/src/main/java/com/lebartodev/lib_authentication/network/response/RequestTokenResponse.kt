package com.lebartodev.lib_authentication.network.response

import com.google.gson.annotations.SerializedName

data class RequestTokenResponse(
    val success: Boolean,
    @SerializedName("expires_at") val expiresAt: String,
    @SerializedName("request_token") val requestToken: String
)
