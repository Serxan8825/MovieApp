package com.serxanallahverdiyev.movieapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MovieDetailDto(
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("release_date")
    val releaseDate: String?,
    val runtime: Int?,
    val genres: List<GenreDto>,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompanyDto>?,
    val tagline: String?
)

data class TvShowDetailDto(
    val id: Int,
    val name: String,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("first_air_date")
    val firstAirDate: String?,
    @SerializedName("number_of_seasons")
    val numberOfSeasons: Int,
    @SerializedName("number_of_episodes")
    val numberOfEpisodes: Int,
    val genres: List<GenreDto>,
    val seasons: List<SeasonDto>
)

data class GenreDto(
    val id: Int,
    val name: String
)

data class GenreListDto(
    val genres: List<GenreDto>
)

data class ProductionCompanyDto(
    val id: Int,
    val name: String,
    @SerializedName("logo_path")
    val logoPath: String?
)

data class SeasonDto(
    val id: Int,
    @SerializedName("air_date")
    val airDate: String?,
    @SerializedName("episode_count")
    val episodeCount: Int,
    val name: String,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("season_number")
    val seasonNumber: Int
)
