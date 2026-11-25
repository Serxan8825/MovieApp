package com.serxanallahverdiyev.movieapp.domain.repository

import com.serxanallahverdiyev.movieapp.core.common.Resource
import com.serxanallahverdiyev.movieapp.domain.model.*
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(fetchFromRemote: Boolean): Flow<Resource<List<Movie>>>
    fun getTopRatedMovies(fetchFromRemote: Boolean): Flow<Resource<List<Movie>>>
    fun getNowPlayingMovies(fetchFromRemote: Boolean): Flow<Resource<List<Movie>>>
    fun getUpcomingMovies(fetchFromRemote: Boolean): Flow<Resource<List<Movie>>>
    
    suspend fun getMovieDetails(movieId: Int): MovieDetail
    suspend fun getMovieCredits(movieId: Int): List<Cast>
    suspend fun getMovieTrailer(movieId: Int): String?
    
    fun getPopularTvShows(fetchFromRemote: Boolean): Flow<Resource<List<TvShow>>>
    fun getTopRatedTvShows(fetchFromRemote: Boolean): Flow<Resource<List<TvShow>>>
    fun getAiringTodayTvShows(fetchFromRemote: Boolean): Flow<Resource<List<TvShow>>>
    suspend fun getTvShowDetails(tvId: Int): TvShowDetail
    suspend fun getTvShowCredits(tvId: Int): List<Cast>
    suspend fun getTvShowTrailer(tvId: Int): String?
    suspend fun getTvSeasonDetails(tvId: Int, seasonNumber: Int): Season
    
    suspend fun getPersonDetails(personId: Int): PersonDetail
    suspend fun getPersonCombinedCredits(personId: Int): List<CombinedCredit>
    
    suspend fun getMovieGenres(): List<Genre>
    suspend fun getTvGenres(): List<Genre>
    fun discoverMoviesByGenre(genreId: Int, page: Int, fetchFromRemote: Boolean): Flow<Resource<List<Movie>>>
    fun discoverTvShowsByGenre(genreId: Int, page: Int, fetchFromRemote: Boolean): Flow<Resource<List<TvShow>>>
    
    suspend fun multiSearch(query: String, page: Int): List<SearchResult>
    
    // Favorites
    fun getAllFavorites(): Flow<List<Favorite>>
    suspend fun addFavorite(favorite: Favorite)
    suspend fun removeFavorite(favorite: Favorite)
    suspend fun isFavorite(id: Int): Boolean
}
