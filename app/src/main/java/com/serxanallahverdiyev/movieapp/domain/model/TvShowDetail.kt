package com.serxanallahverdiyev.movieapp.domain.model

data class TvShowDetail(
    val id: Int,
    val name: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val voteAverage: Double,
    val firstAirDate: String?,
    val numberOfSeasons: Int,
    val numberOfEpisodes: Int,
    val genres: List<Genre>,
    val seasons: List<Season>
)

data class Season(
    val id: Int,
    val airDate: String?,
    val episodeCount: Int,
    val name: String,
    val overview: String,
    val posterPath: String?,
    val seasonNumber: Int
)
