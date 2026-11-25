package com.serxanallahverdiyev.movieapp.presentation.actor_detail

import com.serxanallahverdiyev.movieapp.domain.usecase.ActorFullDetail

data class ActorDetailState(
    val isLoading: Boolean = false,
    val actorDetail: ActorFullDetail? = null,
    val error: String? = null
)

sealed interface ActorDetailEvent {
    data class LoadActorDetail(val id: Int) : ActorDetailEvent
}
