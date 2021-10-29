package com.lebartodev.lib_authentication.network.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class RequestTokenResponse(
    val success: Boolean,
    @SerializedName("expires_at") val expiresAt: Date,
    @SerializedName("request_token") val requestToken: String
)