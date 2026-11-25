package com.serxanallahverdiyev.movieapp.presentation.movie_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serxanallahverdiyev.movieapp.core.common.Resource
import com.serxanallahverdiyev.movieapp.domain.model.Favorite
import com.serxanallahverdiyev.movieapp.domain.usecase.GetMovieDetailsUseCase
import com.serxanallahverdiyev.movieapp.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MovieDetailState())
    val state = _state.asStateFlow()


    fun onEvent(event: MovieDetailEvent) {
        when (event) {
            is MovieDetailEvent.LoadMovieDetail -> getMovieDetail(event.id)
            MovieDetailEvent.ToggleFavorite -> toggleFavorite()
        }
    }

    private fun getMovieDetail(id: Int) {
        getMovieDetailsUseCase(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            movieDetail = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                }
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            error = result.message,
                            isLoading = false
                        )
                    }
                }
                is Resource.Loading -> {
                    _state.update { it.copy(isLoading = true) }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun toggleFavorite() {
        val detail = _state.value.movieDetail?.detail ?: return
        val favorite = Favorite(
            id = detail.id,
            title = detail.title,
            posterPath = detail.posterPath,
            releaseDate = detail.releaseDate,
            voteAverage = detail.voteAverage,
            mediaType = "movie"
        )
        viewModelScope.launch {
            toggleFavoriteUseCase(favorite)
            _state.update {
                it.copy(
                    movieDetail = it.movieDetail?.copy(
                        isFavorite = !it.movieDetail.isFavorite
                    )
                )
            }
        }
    }
}
