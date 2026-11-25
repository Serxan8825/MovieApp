package com.serxanallahverdiyev.movieapp.domain.model

data class Favorite(
    val id: Int,
    val title: String,
    val posterPath: String?,
    val releaseDate: String?,
    val voteAverage: Double,
    val mediaType: String
)
