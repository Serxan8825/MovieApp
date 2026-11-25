package com.serxanallahverdiyev.movieapp.presentation.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.serxanallahverdiyev.movieapp.core.ui.MoviePosterCard
import com.serxanallahverdiyev.movieapp.ui.theme.BackgroundBlack
import com.serxanallahverdiyev.movieapp.ui.theme.PrimaryWhite
import com.serxanallahverdiyev.movieapp.ui.theme.SecondaryGray

@Composable
fun FavoritesScreen(
    onMovieClick: (Int) -> Unit,
    onTvShowClick: (Int) -> Unit,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundBlack)
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "My Favorites",
            style = MaterialTheme.typography.headlineMedium,
            color = PrimaryWhite,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Box(modifier = Modifier.fillMaxSize()) {
            if (state.favorites.isEmpty()) {
                Text(
                    text = "No favorites yet.",
                    color = SecondaryGray,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.favorites) { favorite ->
                    MoviePosterCard(
                        posterPath = favorite.posterPath,
                        title = favorite.title,
                        rating = favorite.voteAverage,
                        releaseDate = favorite.releaseDate,
                        onClick = {
                            if (favorite.mediaType == "movie") {
                                onMovieClick(favorite.id)
                            } else {
                                onTvShowClick(favorite.id)
                            }
                        }
                    )
                }
            }
        }
    }
}
