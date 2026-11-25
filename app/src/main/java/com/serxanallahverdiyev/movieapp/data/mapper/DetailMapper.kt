package com.serxanallahverdiyev.movieapp.data.mapper

import com.serxanallahverdiyev.movieapp.data.local.entity.GenreEntity
import com.serxanallahverdiyev.movieapp.data.remote.dto.*
import com.serxanallahverdiyev.movieapp.domain.model.*

fun MovieDetailDto.toMovieDetail(): MovieDetail {
    return MovieDetail(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage,
        releaseDate = releaseDate,
        runtime = runtime,
        genres = genres.map { it.toGenre() },
        tagline = tagline
    )
}

fun TvShowDetailDto.toTvShowDetail(): TvShowDetail {
    return TvShowDetail(
        id = id,
        name = name,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage,
        firstAirDate = firstAirDate,
        numberOfSeasons = numberOfSeasons,
        numberOfEpisodes = numberOfEpisodes,
        genres = genres.map { it.toGenre() },
        seasons = seasons.map { it.toSeason() }
    )
}

fun GenreDto.toGenre(): Genre {
    return Genre(
        id = id,
        name = name
    )
}

fun GenreDto.toGenreEntity(type: String): GenreEntity {
    return GenreEntity(
        id = id,
        name = name,
        type = type
    )
}

fun GenreEntity.toGenre(): Genre {
    return Genre(
        id = id,
        name = name
    )
}

fun SeasonDto.toSeason(): Season {
    return Season(
        id = id,
        airDate = airDate,
        episodeCount = episodeCount,
        name = name,
        overview = overview,
        posterPath = posterPath,
        seasonNumber = seasonNumber
    )
}

fun CastDto.toCast(): Cast {
    return Cast(
        id = id,
        name = name,
        character = character,
        profilePath = profilePath
    )
}
