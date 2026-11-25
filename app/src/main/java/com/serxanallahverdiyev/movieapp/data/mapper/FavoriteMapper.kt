package com.serxanallahverdiyev.movieapp.data.mapper

import com.serxanallahverdiyev.movieapp.data.local.entity.FavoriteEntity
import com.serxanallahverdiyev.movieapp.domain.model.Favorite

fun FavoriteEntity.toFavorite(): Favorite {
    return Favorite(
        id = id,
        title = title,
        posterPath = posterPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        mediaType = mediaType
    )
}

fun Favorite.toFavoriteEntity(): FavoriteEntity {
    return FavoriteEntity(
        id = id,
        title = title,
        posterPath = posterPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        mediaType = mediaType
    )
}
