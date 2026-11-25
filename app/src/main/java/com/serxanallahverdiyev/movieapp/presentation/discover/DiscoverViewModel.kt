package com.serxanallahverdiyev.movieapp.presentation.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serxanallahverdiyev.movieapp.core.common.Resource
import com.serxanallahverdiyev.movieapp.domain.usecase.GetMovieGenresUseCase
import com.serxanallahverdiyev.movieapp.domain.usecase.GetTvGenresUseCase
import com.serxanallahverdiyev.movieapp.domain.model.Genre
import com.serxanallahverdiyev.movieapp.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val getMovieGenresUseCase: GetMovieGenresUseCase,
    private val getTvGenresUseCase: GetTvGenresUseCase,
    private val repository: MovieRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DiscoverState())
    val state: StateFlow<DiscoverState> = _state.asStateFlow()

    init {
        onEvent(DiscoverEvent.LoadGenres)
    }

    fun onEvent(event: DiscoverEvent) {
        when (event) {
            is DiscoverEvent.LoadGenres -> loadGenres()
            is DiscoverEvent.ChangeCategory -> {
                _state.update { it.copy(selectedCategory = event.category) }
            }
        }
    }

    private fun loadGenres() {
        viewModelScope.launch {
            combine(
                getMovieGenresUseCase(),
                getTvGenresUseCase()
            ) { movieResult, tvResult ->
                val isLoading = movieResult is Resource.Loading || tvResult is Resource.Loading
                val error =
                    if (movieResult is Resource.Error) movieResult.message else if (tvResult is Resource.Error) tvResult.message else null

                val movieGenres = if (movieResult is Resource.Success) movieResult.data ?: emptyList() else emptyList()
                val tvGenres = if (tvResult is Resource.Success) tvResult.data ?: emptyList() else emptyList()

                _state.update {
                    it.copy(
                        isLoading = isLoading,
                        movieGenres = movieGenres,
                        tvGenres = tvGenres,
                        error = error
                    )
                }

                if (movieGenres.isNotEmpty() && tvGenres.isNotEmpty() && !isLoading) {
                    fetchGenreImages()
                }
            }.collect {}
        }
    }

    private fun fetchGenreImages() {
        viewModelScope.launch {
            val movieGenresWithImages = _state.value.movieGenres.map { genre ->
                async {
                    if (genre.backdropPath == null) {
                        val resource = repository.discoverMoviesByGenre(genre.id, 1, true)
                            .first { it !is Resource.Loading }
                        genre.copy(backdropPath = resource.data?.firstOrNull()?.backdropPath)
                    } else genre
                }
            }.awaitAll()

            val tvGenresWithImages = _state.value.tvGenres.map { genre ->
                async {
                    if (genre.backdropPath == null) {
                        val resource = repository.discoverTvShowsByGenre(genre.id, 1, true)
                            .first { it !is Resource.Loading }
                        genre.copy(backdropPath = resource.data?.firstOrNull()?.backdropPath)
                    } else genre
                }
            }.awaitAll()

            _state.update {
                it.copy(
                    movieGenres = movieGenresWithImages,
                    tvGenres = tvGenresWithImages
                )
            }
        }
    }
}
