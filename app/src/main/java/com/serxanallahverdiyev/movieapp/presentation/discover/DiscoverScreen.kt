package com.serxanallahverdiyev.movieapp.presentation.discover

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.serxanallahverdiyev.movieapp.core.common.Constants
import com.serxanallahverdiyev.movieapp.domain.model.Genre
import com.serxanallahverdiyev.movieapp.ui.theme.AccentPurple
import com.serxanallahverdiyev.movieapp.ui.theme.BackgroundBlack
import com.serxanallahverdiyev.movieapp.ui.theme.PrimaryWhite

@Composable
fun DiscoverScreen(
    onGenreClick: (Genre, Boolean) -> Unit,
    viewModel: DiscoverViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundBlack)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        CategorySelector(
            selectedCategory = state.selectedCategory,
            onCategorySelected = { viewModel.onEvent(DiscoverEvent.ChangeCategory(it)) }
        )

        Box(modifier = Modifier.fillMaxSize()) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = AccentPurple
                )
            }

            state.error?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            val genres = if (state.selectedCategory == DiscoverCategory.MOVIES) state.movieGenres else state.tvGenres
            val isMovie = state.selectedCategory == DiscoverCategory.MOVIES

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(top = 8.dp, bottom = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(genres) { genre ->
                    GenreCard(
                        genre = genre,
                        onClick = { onGenreClick(genre, isMovie) }
                    )
                }
            }
        }
    }
}

@Composable
fun CategorySelector(
    selectedCategory: DiscoverCategory,
    onCategorySelected: (DiscoverCategory) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF1C1C1E)),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        CategoryButton(
            text = "Movies",
            isSelected = selectedCategory == DiscoverCategory.MOVIES,
            onClick = { onCategorySelected(DiscoverCategory.MOVIES) },
            modifier = Modifier.weight(1f)
        )
        CategoryButton(
            text = "TV Shows",
            isSelected = selectedCategory == DiscoverCategory.TV_SHOWS,
            onClick = { onCategorySelected(DiscoverCategory.TV_SHOWS) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun CategoryButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(4.dp)
            .height(44.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(if (isSelected) Color(0xFF323246) else Color.Transparent)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (isSelected) PrimaryWhite else Color.Gray,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            fontSize = 16.sp
        )
    }
}

@Composable
fun GenreCard(
    genre: Genre,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF2C2C2E))
            .clickable { onClick() }
    ) {
        if (genre.backdropPath != null) {
            AsyncImage(
                model = Constants.IMAGE_BASE_URL + Constants.BACKDROP_SIZE_ORIGINAL + genre.backdropPath,
                contentDescription = genre.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.6f)
                        )
                    )
                )
        )

        Text(
            text = genre.name,
            color = PrimaryWhite,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Center)
        )

        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = AccentPurple,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(12.dp)
                .size(16.dp)
        )
    }
}
