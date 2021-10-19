package com.lebartodev.lib.data.network

import com.google.gson.annotations.SerializedName
import java.util.*

data class CreditDetailsResponse(
    val id: String?,
    val person: CreditPersonResponse?
)

data class CreditPersonResponse(
    val id: Long,
    @SerializedName("profile_path") val profilePath: String?,
    val name: String?
)

data class PersonResponse(
    val id: Long,
    @SerializedName("profile_path") val profilePath: String?,
    val name: String?,
    val biography: String?,
    val birthday: Date,
    val deathday: Date?,
    val place_of_birth: String?
)
