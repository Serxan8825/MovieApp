package com.serxanallahverdiyev.movieapp.domain.usecase

import com.serxanallahverdiyev.movieapp.core.common.Resource
import com.serxanallahverdiyev.movieapp.domain.model.Movie
import com.serxanallahverdiyev.movieapp.domain.model.TvShow
import com.serxanallahverdiyev.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

data class HomeContent(
    val popularMovies: List<Movie> = emptyList(),
    val nowPlayingMovies: List<Movie> = emptyList(),
    val topRatedMovies: List<Movie> = emptyList(),
    val popularTvShows: List<TvShow> = emptyList()
)

class GetHomeContentUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<Resource<HomeContent>> {
        return combine(
            repository.getPopularMovies(true),
            repository.getNowPlayingMovies(true),
            repository.getTopRatedMovies(true),
            repository.getPopularTvShows(true)
        ) { results ->
            val popular = results[0]
            val nowPlaying = results[1]
            val topRated = results[2]
            val tvShows = results[3]

            val content = HomeContent(
                popularMovies = (popular.data as? List<Movie>) ?: emptyList(),
                nowPlayingMovies = (nowPlaying.data as? List<Movie>) ?: emptyList(),
                topRatedMovies = (topRated.data as? List<Movie>) ?: emptyList(),
                popularTvShows = (tvShows.data as? List<TvShow>) ?: emptyList()
            )

            val isLoading = results.any { it is Resource.Loading }
            val error = results.firstOrNull { it is Resource.Error }?.message

            when {
                error != null && content.popularMovies.isEmpty() -> Resource.Error(error)
                isLoading && content.popularMovies.isEmpty() -> Resource.Loading()
                else -> Resource.Success(content)
            }
        }
    }
}
