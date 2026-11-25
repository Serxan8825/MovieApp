package com.serxanallahverdiyev.movieapp.presentation.actor_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serxanallahverdiyev.movieapp.core.common.Resource
import com.serxanallahverdiyev.movieapp.domain.usecase.GetActorDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ActorDetailViewModel @Inject constructor(
    private val getActorDetailsUseCase: GetActorDetailsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ActorDetailState())
    val state: StateFlow<ActorDetailState> = _state.asStateFlow()

    fun onEvent(event: ActorDetailEvent) {
        when (event) {
            is ActorDetailEvent.LoadActorDetail -> getActorDetail(event.id)
        }
    }

    private fun getActorDetail(id: Int) {
        getActorDetailsUseCase(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            actorDetail = result.data,
                            error = null
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }

                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            isLoading = true,
                            error = null
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}
