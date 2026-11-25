package com.serxanallahverdiyev.movieapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PersonDetailDto(
    val id: Int,
    val name: String,
    val biography: String?,
    @SerializedName("birthday")
    val birthday: String?,
    @SerializedName("deathday")
    val deathday: String?,
    @SerializedName("place_of_birth")
    val placeOfBirth: String?,
    @SerializedName("profile_path")
    val profilePath: String?,
    @SerializedName("known_for_department")
    val knownForDepartment: String?
)

data class CombinedCreditsDto(
    val id: Int,
    val cast: List<CombinedCastDto>,
    val crew: List<CombinedCrewDto>
)

data class CombinedCastDto(
    val id: Int,
    val title: String?,
    val name: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("media_type")
    val mediaType: String,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("first_air_date")
    val firstAirDate: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?
)

data class CombinedCrewDto(
    val id: Int,
    val title: String?,
    val name: String?,
    @SerializedName("media_type")
    val mediaType: String
)
