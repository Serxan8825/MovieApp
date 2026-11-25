package com.serxanallahverdiyev.movieapp.domain.usecase

import com.serxanallahverdiyev.movieapp.core.common.Resource
import com.serxanallahverdiyev.movieapp.domain.model.Movie
import com.serxanallahverdiyev.movieapp.domain.model.TvShow
import com.serxanallahverdiyev.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DiscoverMoviesByGenreUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(genreId: Int, page: Int = 1, fetchFromRemote: Boolean = true): Flow<Resource<List<Movie>>> {
        return repository.discoverMoviesByGenre(genreId, page, fetchFromRemote)
    }
}

class DiscoverTvShowsByGenreUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(genreId: Int, page: Int = 1, fetchFromRemote: Boolean = true): Flow<Resource<List<TvShow>>> {
        return repository.discoverTvShowsByGenre(genreId, page, fetchFromRemote)
    }
}
