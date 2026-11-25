package com.serxanallahverdiyev.movieapp.presentation.home

import com.serxanallahverdiyev.movieapp.domain.usecase.HomeContent

data class HomeState(
    val isLoading: Boolean = false,
    val content: HomeContent? = null,
    val error: String? = null
)

sealed interface HomeEvent {
    data object Refresh : HomeEvent
}
