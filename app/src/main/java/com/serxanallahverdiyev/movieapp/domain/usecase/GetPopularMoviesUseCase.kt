package com.serxanallahverdiyev.movieapp.domain.usecase

import com.serxanallahverdiyev.movieapp.core.common.Resource
import com.serxanallahverdiyev.movieapp.domain.model.Movie
import com.serxanallahverdiyev.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(fetchFromRemote: Boolean = true): Flow<Resource<List<Movie>>> {
        return repository.getPopularMovies(fetchFromRemote)
    }
}
