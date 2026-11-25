package com.serxanallahverdiyev.movieapp.presentation.genre_results

import com.serxanallahverdiyev.movieapp.domain.model.Movie
import com.serxanallahverdiyev.movieapp.domain.model.TvShow

data class GenreResultsState(
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val tvShows: List<TvShow> = emptyList(),
    val error: String? = null
)

sealed interface GenreResultsEvent {
    data class LoadResults(val genreId: Int, val isMovie: Boolean) : GenreResultsEvent
}
