package com.serxanallahverdiyev.movieapp.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serxanallahverdiyev.movieapp.domain.usecase.GetFavoritesUseCase
import com.serxanallahverdiyev.movieapp.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(FavoritesState())
    val state: StateFlow<FavoritesState> = _state.asStateFlow()

    init {
        onEvent(FavoritesEvent.LoadFavorites)
    }

    fun onEvent(event: FavoritesEvent) {
        when (event) {
            is FavoritesEvent.LoadFavorites -> loadFavorites()
            is FavoritesEvent.RemoveFromFavorites -> removeFromFavorites(event.favorite)
        }
    }

    private fun loadFavorites() {
        getFavoritesUseCase().onEach { favorites ->
            _state.update { it.copy(favorites = favorites) }
        }.launchIn(viewModelScope)
    }

    private fun removeFromFavorites(favorite: com.serxanallahverdiyev.movieapp.domain.model.Favorite) {
        viewModelScope.launch {
            toggleFavoriteUseCase(favorite)
        }
    }
}
