package com.serxanallahverdiyev.movieapp.presentation.discover

import com.serxanallahverdiyev.movieapp.domain.model.Genre

enum class DiscoverCategory {
    MOVIES, TV_SHOWS
}

data class DiscoverState(
    val isLoading: Boolean = false,
    val movieGenres: List<Genre> = emptyList(),
    val tvGenres: List<Genre> = emptyList(),
    val selectedCategory: DiscoverCategory = DiscoverCategory.MOVIES,
    val error: String? = null
)

sealed interface DiscoverEvent {
    data object LoadGenres : DiscoverEvent
    data class ChangeCategory(val category: DiscoverCategory) : DiscoverEvent
}
