package com.serxanallahverdiyev.movieapp.presentation.actor_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.serxanallahverdiyev.movieapp.core.common.Constants
import com.serxanallahverdiyev.movieapp.core.ui.MoviePosterCard
import com.serxanallahverdiyev.movieapp.core.ui.SectionHeader
import com.serxanallahverdiyev.movieapp.ui.theme.BackgroundBlack
import com.serxanallahverdiyev.movieapp.ui.theme.PrimaryWhite
import com.serxanallahverdiyev.movieapp.ui.theme.SecondaryGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActorDetailScreen(
    actorId: Int,
    onBackClick: () -> Unit,
    onMovieClick: (Int) -> Unit,
    onTvShowClick: (Int) -> Unit,
    viewModel: ActorDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(actorId) {
        viewModel.onEvent(ActorDetailEvent.LoadActorDetail(actorId))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Actor Details") },
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

            state.actorDetail?.let { actorFullDetail ->
                val actor = actorFullDetail.detail
                val credits = actorFullDetail.credits

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AsyncImage(
                                model = Constants.IMAGE_BASE_URL + Constants.PROFILE_SIZE_W185 + actor.profilePath,
                                contentDescription = actor.name,
                                modifier = Modifier
                                    .size(180.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            Text(
                                text = actor.name,
                                style = MaterialTheme.typography.headlineMedium,
                                color = PrimaryWhite,
                                fontWeight = FontWeight.Bold
                            )
                            
                            actor.knownForDepartment?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = SecondaryGray
                                )
                            }
                        }
                    }

                    item {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Biography",
                                style = MaterialTheme.typography.titleLarge,
                                color = PrimaryWhite,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = actor.biography ?: "No biography available.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = SecondaryGray,
                                lineHeight = 20.sp
                            )
                        }
                    }

                    if (credits.isNotEmpty()) {
                        item {
                            SectionHeader(title = "Known For")
                            LazyRow(
                                contentPadding = PaddingValues(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(credits) { credit ->
                                    MoviePosterCard(
                                        posterPath = credit.posterPath,
                                        title = credit.title ?: credit.name ?: "",
                                        rating = credit.voteAverage ?: 0.0,
                                        releaseDate = credit.releaseDate ?: credit.firstAirDate,
                                        onClick = {
                                            if (credit.mediaType == "movie") {
                                                onMovieClick(credit.id)
                                            } else {
                                                onTvShowClick(credit.id)
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
