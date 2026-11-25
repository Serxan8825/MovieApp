package com.serxanallahverdiyev.movieapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serxanallahverdiyev.movieapp.core.common.Resource
import com.serxanallahverdiyev.movieapp.domain.usecase.GetHomeContentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeContentUseCase: GetHomeContentUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        getHomeContent()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.Refresh -> getHomeContent()
        }
    }

    private fun getHomeContent() {
        getHomeContentUseCase()
            .onEach { result ->
                _state.update { currentState ->
                    when (result) {
                        is Resource.Success -> {
                            currentState.copy(
                                content = result.data,
                                isLoading = false,
                                error = null
                            )
                        }
                        is Resource.Error -> {
                            currentState.copy(
                                error = result.message,
                                isLoading = false
                            )
                        }
                        is Resource.Loading -> {
                            currentState.copy(
                                isLoading = true,
                                error = null
                            )
                        }
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}
