package com.serxanallahverdiyev.movieapp.presentation.tv_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serxanallahverdiyev.movieapp.core.common.Resource
import com.serxanallahverdiyev.movieapp.domain.model.Favorite
import com.serxanallahverdiyev.movieapp.domain.usecase.GetTvShowDetailsUseCase
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
class TvShowDetailViewModel @Inject constructor(
    private val getTvShowDetailsUseCase: GetTvShowDetailsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TvShowDetailState())
    val state = _state.asStateFlow()


    fun onEvent(event: TvShowDetailEvent) {
        when (event) {
            is TvShowDetailEvent.LoadTvShowDetail -> getTvShowDetail(event.id)
            TvShowDetailEvent.ToggleFavorite -> toggleFavorite()
        }
    }

    private fun getTvShowDetail(id: Int) {
        getTvShowDetailsUseCase(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            tvShowDetail = result.data,
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
        val detail = _state.value.tvShowDetail?.detail ?: return
        val favorite = Favorite(
            id = detail.id,
            title = detail.name,
            posterPath = detail.posterPath,
            releaseDate = detail.firstAirDate,
            voteAverage = detail.voteAverage,
            mediaType = "tv"
        )
        viewModelScope.launch {
            toggleFavoriteUseCase(favorite)
            _state.update {
                it.copy(
                    tvShowDetail = it.tvShowDetail?.copy(
                        isFavorite = !it.tvShowDetail.isFavorite
                    )
                )
            }
        }
    }
}
