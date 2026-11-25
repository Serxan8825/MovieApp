package com.serxanallahverdiyev.movieapp.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface MovieNavKey : NavKey {
    @Serializable data object Home : MovieNavKey
    @Serializable data object Discover : MovieNavKey
    @Serializable data object Favorites : MovieNavKey
    @Serializable data object Search : MovieNavKey
    @Serializable data class MovieDetail(val id: Int) : MovieNavKey
    @Serializable data class TvShowDetail(val id: Int) : MovieNavKey
    @Serializable data class ActorDetail(val id: Int) : MovieNavKey
    @Serializable data class GenreResults(val genreId: Int, val genreName: String, val isMovie: Boolean) : MovieNavKey

    val isBottomBarScreen: Boolean
        get() = this is Home || this is Discover || this is Favorites || this is Search
}
