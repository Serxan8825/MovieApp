package com.serxanallahverdiyev.movieapp.domain.model

data class PersonDetail(
    val id: Int,
    val name: String,
    val biography: String?,
    val birthday: String?,
    val deathday: String?,
    val placeOfBirth: String?,
    val profilePath: String?,
    val knownForDepartment: String?
)

data class CombinedCredit(
    val id: Int,
    val title: String?,
    val name: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val mediaType: String,
    val releaseDate: String?,
    val firstAirDate: String?,
    val voteAverage: Double?
)
