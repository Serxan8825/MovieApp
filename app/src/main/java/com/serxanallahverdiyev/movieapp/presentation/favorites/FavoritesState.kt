package com.serxanallahverdiyev.movieapp.presentation.favorites

import com.serxanallahverdiyev.movieapp.domain.model.Favorite

data class FavoritesState(
    val favorites: List<Favorite> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed interface FavoritesEvent {
    data object LoadFavorites : FavoritesEvent
    data class RemoveFromFavorites(val favorite: Favorite) : FavoritesEvent
}
