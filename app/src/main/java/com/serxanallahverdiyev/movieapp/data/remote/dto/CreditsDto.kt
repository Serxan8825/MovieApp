package com.serxanallahverdiyev.movieapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CreditsDto(
    val id: Int,
    val cast: List<CastDto>,
    val crew: List<CrewDto>
)

data class CastDto(
    val id: Int,
    val name: String,
    @SerializedName("character")
    val character: String,
    @SerializedName("profile_path")
    val profilePath: String?,
    val order: Int
)

data class CrewDto(
    val id: Int,
    val name: String,
    val job: String,
    @SerializedName("profile_path")
    val profilePath: String?
)
