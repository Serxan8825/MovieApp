package com.serxanallahverdiyev.movieapp.presentation.tv_detail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.serxanallahverdiyev.movieapp.core.common.Constants
import com.serxanallahverdiyev.movieapp.presentation.movie_detail.ActorItem
import com.serxanallahverdiyev.movieapp.ui.theme.AccentPurple
import com.serxanallahverdiyev.movieapp.ui.theme.PrimaryWhite
import com.serxanallahverdiyev.movieapp.ui.theme.RatingYellow
import com.serxanallahverdiyev.movieapp.ui.theme.SecondaryGray

@Composable
fun TvShowDetailScreen(
    tvShowId: Int,
    onBackClick: () -> Unit,
    onActorClick: (Int) -> Unit,
    viewModel: TvShowDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(tvShowId) {
        viewModel.onEvent(TvShowDetailEvent.LoadTvShowDetail(tvShowId))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        state.error?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.Center).padding(16.dp),
                textAlign = TextAlign.Center
            )
        }

        state.tvShowDetail?.let { fullDetail ->
            val tv = fullDetail.detail
            
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                // Header with Backdrop
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    ) {
                        AsyncImage(
                            model = Constants.IMAGE_BASE_URL + Constants.BACKDROP_SIZE_ORIGINAL + tv.backdropPath,
                            contentDescription = tv.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            MaterialTheme.colorScheme.background
                                        ),
                                        startY = 400f
                                    )
                                )
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 48.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            IconButton(
                                onClick = onBackClick,
                                modifier = Modifier.background(Color.Black.copy(alpha = 0.5f), CircleShape)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back",
                                    tint = PrimaryWhite
                                )
                            }
                            
                            IconButton(
                                onClick = { viewModel.onEvent(TvShowDetailEvent.ToggleFavorite) },
                                modifier = Modifier.background(Color.Black.copy(alpha = 0.5f), CircleShape)
                            ) {
                                Icon(
                                    imageVector = if (fullDetail.isFavorite) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                    contentDescription = "Favorite",
                                    tint = if (fullDetail.isFavorite) AccentPurple else PrimaryWhite
                                )
                            }
                        }
                    }
                }

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .offset(y = (-40).dp),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .width(120.dp)
                                .height(180.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                        ) {
                            AsyncImage(
                                model = Constants.IMAGE_BASE_URL + Constants.POSTER_SIZE_W500 + tv.posterPath,
                                contentDescription = tv.name,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(
                            modifier = Modifier.padding(bottom = 8.dp)
                        ) {
                            Text(
                                text = tv.name,
                                style = MaterialTheme.typography.headlineSmall,
                                color = PrimaryWhite,
                                fontWeight = FontWeight.Bold
                            )
                            
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = RatingYellow,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = String.format("%.1f", tv.voteAverage),
                                    color = PrimaryWhite,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            }
                            
                            Text(
                                text = "${tv.firstAirDate?.take(4)} • ${tv.numberOfSeasons} Seasons",
                                color = SecondaryGray,
                                fontSize = 14.sp
                            )
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            Button(
                                onClick = {
                                    fullDetail.trailerUrl?.let { key ->
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(Constants.YOUTUBE_URL + key))
                                        context.startActivity(intent)
                                    }
                                },
                                enabled = fullDetail.trailerUrl != null,
                                colors = ButtonDefaults.buttonColors(containerColor = AccentPurple),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(Icons.Default.PlayArrow, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = "Watch Trailer", color = PrimaryWhite)
                            }
                        }
                    }
                }


                item {
                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                        Text(
                            text = "Overview",
                            style = MaterialTheme.typography.titleLarge,
                            color = PrimaryWhite,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = tv.overview,
                            style = MaterialTheme.typography.bodyMedium,
                            color = SecondaryGray,
                            lineHeight = 20.sp
                        )
                    }
                }


                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Seasons",
                        style = MaterialTheme.typography.titleLarge,
                        color = PrimaryWhite,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(tv.seasons) { season ->
                            Column(modifier = Modifier.width(100.dp)) {
                                AsyncImage(
                                    model = Constants.IMAGE_BASE_URL + Constants.POSTER_SIZE_W500 + season.posterPath,
                                    contentDescription = season.name,
                                    modifier = Modifier
                                        .height(150.dp)
                                        .clip(RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = season.name,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = PrimaryWhite,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1
                                )
                                Text(
                                    text = "${season.episodeCount} Eps",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = SecondaryGray
                                )
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Cast",
                        style = MaterialTheme.typography.titleLarge,
                        color = PrimaryWhite,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(fullDetail.cast) { actor ->
                            ActorItem(
                                actor = actor,
                                onClick = { onActorClick(actor.id) }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}
