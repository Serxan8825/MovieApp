package com.serxanallahverdiyev.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberDecoratedNavEntries
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.serxanallahverdiyev.movieapp.presentation.actor_detail.ActorDetailScreen
import com.serxanallahverdiyev.movieapp.presentation.discover.DiscoverScreen
import com.serxanallahverdiyev.movieapp.presentation.favorites.FavoritesScreen
import com.serxanallahverdiyev.movieapp.presentation.genre_results.GenreResultsScreen
import com.serxanallahverdiyev.movieapp.presentation.home.HomeScreen
import com.serxanallahverdiyev.movieapp.presentation.movie_detail.MovieDetailScreen
import com.serxanallahverdiyev.movieapp.presentation.navigation.MovieNavKey
import com.serxanallahverdiyev.movieapp.presentation.search.SearchScreen
import com.serxanallahverdiyev.movieapp.presentation.tv_detail.TvShowDetailScreen
import com.serxanallahverdiyev.movieapp.ui.theme.*
import dagger.hilt.android.AndroidEntryPoint

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val key: MovieNavKey
)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieAppTheme {
                val backStack = rememberNavBackStack(MovieNavKey.Home)
                
                fun popBack() {
                    if (backStack.size > 1) {
                        backStack.removeAt(backStack.size - 1)
                    }
                }

                val entryProvider = remember {
                    entryProvider<NavKey> {
                        entry<MovieNavKey.Home> {
                            HomeScreen(
                                onMovieClick = { id -> backStack.add(MovieNavKey.MovieDetail(id)) },
                                onTvShowClick = { id -> backStack.add(MovieNavKey.TvShowDetail(id)) },
                                onSearchClick = { backStack.add(MovieNavKey.Search) }
                            )
                        }
                        entry<MovieNavKey.Discover> {
                            DiscoverScreen(
                                onGenreClick = { genre, isMovie ->
                                    backStack.add(MovieNavKey.GenreResults(genre.id, genre.name, isMovie))
                                }
                            )
                        }
                        entry<MovieNavKey.Favorites> {
                            FavoritesScreen(
                                onMovieClick = { id -> backStack.add(MovieNavKey.MovieDetail(id)) },
                                onTvShowClick = { id -> backStack.add(MovieNavKey.TvShowDetail(id)) }
                            )
                        }
                        entry<MovieNavKey.Search> {
                            SearchScreen(
                                onMovieClick = { id -> backStack.add(MovieNavKey.MovieDetail(id)) },
                                onTvShowClick = { id -> backStack.add(MovieNavKey.TvShowDetail(id)) },
                                onActorClick = { id -> backStack.add(MovieNavKey.ActorDetail(id)) }
                            )
                        }
                        entry<MovieNavKey.MovieDetail> { key ->
                            MovieDetailScreen(
                                movieId = key.id,
                                onBackClick = { popBack() },
                                onActorClick = { id -> backStack.add(MovieNavKey.ActorDetail(id)) }
                            )
                        }
                        entry<MovieNavKey.TvShowDetail> { key ->
                            TvShowDetailScreen(
                                tvShowId = key.id,
                                onBackClick = { popBack() },
                                onActorClick = { id -> backStack.add(MovieNavKey.ActorDetail(id)) }
                            )
                        }
                        entry<MovieNavKey.ActorDetail> { key ->
                            ActorDetailScreen(
                                actorId = key.id,
                                onBackClick = { popBack() },
                                onMovieClick = { id -> backStack.add(MovieNavKey.MovieDetail(id)) },
                                onTvShowClick = { id -> backStack.add(MovieNavKey.TvShowDetail(id)) }
                            )
                        }
                        entry<MovieNavKey.GenreResults> { key ->
                            GenreResultsScreen(
                                genreId = key.genreId,
                                genreName = key.genreName,
                                isMovie = key.isMovie,
                                onBackClick = { popBack() },
                                onMovieClick = { id -> backStack.add(MovieNavKey.MovieDetail(id)) },
                                onTvShowClick = { id -> backStack.add(MovieNavKey.TvShowDetail(id)) }
                            )
                        }
                    }
                }

                Scaffold(
                    bottomBar = {
                        val currentKey = backStack.lastOrNull()
                        if (currentKey is MovieNavKey && currentKey.isBottomBarScreen) {
                            NavigationBar(containerColor = BackgroundBlack) {
                                val items = listOf(
                                    BottomNavItem("Home", Icons.Default.Home, MovieNavKey.Home),
                                    BottomNavItem("Discover", Icons.Default.Explore, MovieNavKey.Discover),
                                    BottomNavItem("Favorites", Icons.Default.Favorite, MovieNavKey.Favorites),
                                    BottomNavItem("Search", Icons.Default.Search, MovieNavKey.Search)
                                )
                                
                                items.forEach { item ->
                                    NavigationBarItem(
                                        icon = { Icon(item.icon, contentDescription = item.label) },
                                        label = { Text(item.label) },
                                        selected = currentKey == item.key,
                                        onClick = {
                                            if (currentKey != item.key) {
                                                backStack.clear()
                                                backStack.add(item.key)
                                            }
                                        },
                                        colors = NavigationBarItemDefaults.colors(
                                            selectedIconColor = AccentPurple,
                                            selectedTextColor = AccentPurple,
                                            unselectedIconColor = SecondaryGray,
                                            unselectedTextColor = SecondaryGray,
                                            indicatorColor = Color.Transparent
                                        )
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavDisplay(
                            entries = rememberDecoratedNavEntries(
                                backStack = backStack,
                                entryProvider = entryProvider
                            ),
                            onBack = { popBack() }
                        )
                    }
                }
            }
        }
    }
}
