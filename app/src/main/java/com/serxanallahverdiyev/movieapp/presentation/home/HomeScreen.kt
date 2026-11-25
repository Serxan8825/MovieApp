package com.serxanallahverdiyev.movieapp.presentation.home

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.serxanallahverdiyev.movieapp.core.common.Constants
import com.serxanallahverdiyev.movieapp.core.ui.FeaturedMovieCard
import com.serxanallahverdiyev.movieapp.core.ui.MoviePosterCard
import com.serxanallahverdiyev.movieapp.core.ui.SectionHeader
import com.serxanallahverdiyev.movieapp.ui.theme.AccentPurple
import com.serxanallahverdiyev.movieapp.ui.theme.PrimaryWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onMovieClick: (Int) -> Unit,
    onTvShowClick: (Int) -> Unit,
    onSearchClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Movie App",
                        color = AccentPurple,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                },
                actions = {
                    IconButton(onClick = onSearchClick) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = PrimaryWhite
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (state.isLoading && state.content == null) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            if (state.error != null && state.content == null) {
                Text(
                    text = state.error!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            }

            state.content?.let { content ->
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    item {
                        val featuredMovies = content.popularMovies.take(5)
                        val pagerState = rememberPagerState(pageCount = { featuredMovies.size })

                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            pageSpacing = 12.dp
                        ) { page ->
                            val movie = featuredMovies[page]
                            FeaturedMovieCard(
                                backdropPath = movie.backdropPath,
                                title = movie.title,
                                rating = movie.voteAverage,
                                releaseDate = movie.releaseDate,
                                genres = "Movie",
                                onTrailerClick = {
                                    onMovieClick(movie.id)
                                },
                                onCardClick = { onMovieClick(movie.id) }
                            )
                        }
                    }

                    // Popular Movies
                    item {
                        SectionHeader(title = "Popular Movies")
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(content.popularMovies) { movie ->
                                MoviePosterCard(
                                    posterPath = movie.posterPath,
                                    title = movie.title,
                                    rating = movie.voteAverage,
                                    releaseDate = movie.releaseDate,
                                    onClick = { onMovieClick(movie.id) }
                                )
                            }
                        }
                    }

                    // Now Playing
                    item {
                        SectionHeader(title = "Now Playing")
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(content.nowPlayingMovies) { movie ->
                                MoviePosterCard(
                                    posterPath = movie.posterPath,
                                    title = movie.title,
                                    rating = movie.voteAverage,
                                    releaseDate = movie.releaseDate,
                                    onClick = { onMovieClick(movie.id) }
                                )
                            }
                        }
                    }

                    // Popular TV Shows
                    item {
                        SectionHeader(title = "Popular TV Shows")
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(content.popularTvShows) { tv ->
                                MoviePosterCard(
                                    posterPath = tv.posterPath,
                                    title = tv.name,
                                    rating = tv.voteAverage,
                                    releaseDate = tv.firstAirDate,
                                    onClick = { onTvShowClick(tv.id) }
                                )
                            }
                        }
                    }
                    
                    // Top Rated Movies
                    item {
                        SectionHeader(title = "Top Rated")
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(content.topRatedMovies) { movie ->
                                MoviePosterCard(
                                    posterPath = movie.posterPath,
                                    title = movie.title,
                                    rating = movie.voteAverage,
                                    releaseDate = movie.releaseDate,
                                    onClick = { onMovieClick(movie.id) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
