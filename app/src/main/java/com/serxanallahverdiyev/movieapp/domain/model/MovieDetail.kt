package com.serxanallahverdiyev.movieapp.domain.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val voteAverage: Double,
    val releaseDate: String?,
    val runtime: Int?,
    val genres: List<Genre>,
    val tagline: String?
)

data class Genre(
    val id: Int,
    val name: String,
    val backdropPath: String? = null
)
