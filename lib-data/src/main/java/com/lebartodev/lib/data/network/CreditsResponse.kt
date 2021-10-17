package com.lebartodev.lib.data.network

import com.google.gson.annotations.SerializedName

data class CreditsResponse(
    val id: Long,
    val cast: List<CastResponse>?
)

data class CastResponse(
    val id: Long,
    val name: String?,
    @SerializedName("original_name") val originalName: String?,
    @SerializedName("cast_id") val castId: Long?,
    @SerializedName("credit_id") val creditId: String?,
    @SerializedName("character") val character: String?,
    @SerializedName("profile_path") val profilePath: String?,
    val order: Int?,
)