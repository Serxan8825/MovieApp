package com.serxanallahverdiyev.movieapp.presentation.genre_results

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenreResultsScreen(
    genreId: Int,
    genreName: String,
    isMovie: Boolean,
    onBackClick: () -> Unit,
    onMovieClick: (Int) -> Unit,
    onTvShowClick: (Int) -> Unit,
    viewModel: GenreResultsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(genreId, isMovie) {
        viewModel.onEvent(GenreResultsEvent.LoadResults(genreId, isMovie))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = genreName) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = PrimaryWhite
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BackgroundBlack,
                    titleContentColor = PrimaryWhite
                )
            )
        },
        containerColor = BackgroundBlack
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )
            }

            state.error?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (isMovie) {
                    items(state.movies) { movie ->
                        MoviePosterCard(
                            posterPath = movie.posterPath,
                            title = movie.title,
                            rating = movie.voteAverage,
                            releaseDate = movie.releaseDate,
                            onClick = { onMovieClick(movie.id) }
                        )
                    }
                } else {
                    items(state.tvShows) { tvShow ->
                        MoviePosterCard(
                            posterPath = tvShow.posterPath,
                            title = tvShow.name,
                            rating = tvShow.voteAverage,
                            releaseDate = tvShow.firstAirDate,
                            onClick = { onTvShowClick(tvShow.id) }
                        )
                    }
                }
            }
        }
    }
}
