package com.serxanallahverdiyev.movieapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MultiSearchListDto(
    val page: Int,
    val results: List<MultiSearchDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)

data class MultiSearchDto(
    val id: Int,
    val name: String?,
    val title: String?,
    @SerializedName("media_type")
    val mediaType: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("profile_path")
    val profilePath: String?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("first_air_date")
    val firstAirDate: String?
)
