package com.serxanallahverdiyev.movieapp.presentation.genre_results

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serxanallahverdiyev.movieapp.core.common.Resource
import com.serxanallahverdiyev.movieapp.domain.usecase.DiscoverMoviesByGenreUseCase
import com.serxanallahverdiyev.movieapp.domain.usecase.DiscoverTvShowsByGenreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class GenreResultsViewModel @Inject constructor(
    private val discoverMoviesByGenreUseCase: DiscoverMoviesByGenreUseCase,
    private val discoverTvShowsByGenreUseCase: DiscoverTvShowsByGenreUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(GenreResultsState())
    val state: StateFlow<GenreResultsState> = _state.asStateFlow()

    fun onEvent(event: GenreResultsEvent) {
        when (event) {
            is GenreResultsEvent.LoadResults -> loadResults(event.genreId, event.isMovie)
        }
    }

    private fun loadResults(genreId: Int, isMovie: Boolean) {
        if (isMovie) {
            discoverMoviesByGenreUseCase(genreId).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.update {
                            it.copy(isLoading = false, movies = result.data ?: emptyList(), error = null)
                        }
                    }
                    is Resource.Error -> {
                        _state.update { it.copy(isLoading = false, error = result.message) }
                    }
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true, error = null) }
                    }
                }
            }.launchIn(viewModelScope)
        } else {
            discoverTvShowsByGenreUseCase(genreId).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.update {
                            it.copy(isLoading = false, tvShows = result.data ?: emptyList(), error = null)
                        }
                    }
                    is Resource.Error -> {
                        _state.update { it.copy(isLoading = false, error = result.message) }
                    }
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true, error = null) }
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}
