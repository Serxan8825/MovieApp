package com.serxanallahverdiyev.movieapp.data.mapper

import com.serxanallahverdiyev.movieapp.data.remote.dto.CombinedCastDto
import com.serxanallahverdiyev.movieapp.data.remote.dto.PersonDetailDto
import com.serxanallahverdiyev.movieapp.domain.model.CombinedCredit
import com.serxanallahverdiyev.movieapp.domain.model.PersonDetail

fun PersonDetailDto.toPersonDetail(): PersonDetail {
    return PersonDetail(
        id = id,
        name = name,
        biography = biography,
        birthday = birthday,
        deathday = deathday,
        placeOfBirth = placeOfBirth,
        profilePath = profilePath,
        knownForDepartment = knownForDepartment
    )
}

fun CombinedCastDto.toCombinedCredit(): CombinedCredit {
    return CombinedCredit(
        id = id,
        title = title,
        name = name,
        posterPath = posterPath,
        backdropPath = backdropPath,
        mediaType = mediaType,
        releaseDate = releaseDate,
        firstAirDate = firstAirDate,
        voteAverage = voteAverage
    )
}
