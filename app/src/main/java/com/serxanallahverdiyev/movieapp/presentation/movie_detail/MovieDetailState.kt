package com.serxanallahverdiyev.movieapp.presentation.movie_detail

import com.serxanallahverdiyev.movieapp.domain.usecase.MovieFullDetail

data class MovieDetailState(
    val isLoading: Boolean = false,
    val movieDetail: MovieFullDetail? = null,
    val error: String? = null
)

sealed interface MovieDetailEvent {
    data class LoadMovieDetail(val id: Int) : MovieDetailEvent
    data object ToggleFavorite : MovieDetailEvent
}
