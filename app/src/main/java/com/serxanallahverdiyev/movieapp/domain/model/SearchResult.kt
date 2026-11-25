package com.serxanallahverdiyev.movieapp.domain.model

data class SearchResult(
    val id: Int,
    val name: String?,
    val title: String?,
    val mediaType: String,
    val posterPath: String?,
    val profilePath: String?,
    val backdropPath: String?,
    val voteAverage: Double?,
    val releaseDate: String?,
    val firstAirDate: String?
)
