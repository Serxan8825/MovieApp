package com.serxanallahverdiyev.movieapp.domain.usecase

import com.serxanallahverdiyev.movieapp.core.common.Resource
import com.serxanallahverdiyev.movieapp.domain.model.Genre
import com.serxanallahverdiyev.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMovieGenresUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<Resource<List<Genre>>> = flow {
        emit(Resource.Loading())
        val genres = repository.getMovieGenres()
        if (genres.isNotEmpty()) {
            emit(Resource.Success(genres))
        } else {
            emit(Resource.Error("No genres found. Please check your internet connection."))
        }
    }
}

class GetTvGenresUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<Resource<List<Genre>>> = flow {
        emit(Resource.Loading())
        val genres = repository.getTvGenres()
        if (genres.isNotEmpty()) {
            emit(Resource.Success(genres))
        } else {
            emit(Resource.Error("No genres found. Please check your internet connection."))
        }
    }
}
