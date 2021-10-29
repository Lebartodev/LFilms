package com.lebartodev.lib_authentication.network.request

import com.google.gson.annotations.SerializedName

data class SessionIdRequest(@SerializedName("request_token") val requestToken: String)