package com.lebartodev.lib_authentication.network.response

import com.google.gson.annotations.SerializedName

data class AccountResponse(
    val id: Long,
    @SerializedName("name") val name: String?,
    @SerializedName("username") val userName: String?,
)