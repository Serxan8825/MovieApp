package com.serxanallahverdiyev.movieapp.data.mapper

import com.serxanallahverdiyev.movieapp.data.local.entity.TvShowEntity
import com.serxanallahverdiyev.movieapp.data.remote.dto.TvShowDto
import com.serxanallahverdiyev.movieapp.domain.model.TvShow

fun TvShowDto.toTvShow(): TvShow {
    return TvShow(
        id = id,
        name = name,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage,
        firstAirDate = firstAirDate,
        genreIds = genreIds
    )
}

fun TvShowDto.toTvShowEntity(category: String): TvShowEntity {
    return TvShowEntity(
        id = id,
        name = name,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage,
        firstAirDate = firstAirDate,
        category = category
    )
}

fun TvShowEntity.toTvShow(): TvShow {
    return TvShow(
        id = id,
        name = name,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage,
        firstAirDate = firstAirDate,
        genreIds = emptyList()
    )
}
