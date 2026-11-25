package com.serxanallahverdiyev.movieapp.presentation.tv_detail

import com.serxanallahverdiyev.movieapp.domain.usecase.TvShowFullDetail

data class TvShowDetailState(
    val isLoading: Boolean = false,
    val tvShowDetail: TvShowFullDetail? = null,
    val error: String? = null
)

sealed interface TvShowDetailEvent {
    data class LoadTvShowDetail(val id: Int) : TvShowDetailEvent
    data object ToggleFavorite : TvShowDetailEvent
}
