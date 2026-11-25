package com.serxanallahverdiyev.movieapp.data.repository

import com.serxanallahverdiyev.movieapp.core.common.Resource
import com.serxanallahverdiyev.movieapp.data.local.dao.FavoriteDao
import com.serxanallahverdiyev.movieapp.data.local.dao.GenreDao
import com.serxanallahverdiyev.movieapp.data.local.dao.MovieDao
import com.serxanallahverdiyev.movieapp.data.local.dao.TvShowDao
import com.serxanallahverdiyev.movieapp.data.mapper.*
import com.serxanallahverdiyev.movieapp.data.remote.api.TmdbApi
import com.serxanallahverdiyev.movieapp.domain.model.*
import com.serxanallahverdiyev.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: TmdbApi,
    private val favoriteDao: FavoriteDao,
    private val movieDao: MovieDao,
    private val tvShowDao: TvShowDao,
    private val genreDao: GenreDao
) : MovieRepository {

    override fun getPopularMovies(fetchFromRemote: Boolean): Flow<Resource<List<Movie>>> = flow {
        val initialData = movieDao.getMoviesByCategory("popular").first().map { it.toMovie() }
        emit(Resource.Loading(initialData))
        
        if (fetchFromRemote) {
            try {
                val remoteMovies = api.getPopularMovies().results
                movieDao.updateMoviesByCategory("popular", remoteMovies.map { it.toMovieEntity("popular") })
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "Network error", initialData))
            }
        }
        
        movieDao.getMoviesByCategory("popular").collect { entities ->
            emit(Resource.Success(entities.map { it.toMovie() }))
        }
    }

    override fun getTopRatedMovies(fetchFromRemote: Boolean): Flow<Resource<List<Movie>>> = flow {
        val initialData = movieDao.getMoviesByCategory("top_rated").first().map { it.toMovie() }
        emit(Resource.Loading(initialData))

        if (fetchFromRemote) {
            try {
                val remoteMovies = api.getTopRatedMovies().results
                movieDao.updateMoviesByCategory("top_rated", remoteMovies.map { it.toMovieEntity("top_rated") })
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "Network error", initialData))
            }
        }
        movieDao.getMoviesByCategory("top_rated").collect { entities ->
            emit(Resource.Success(entities.map { it.toMovie() }))
        }
    }

    override fun getNowPlayingMovies(fetchFromRemote: Boolean): Flow<Resource<List<Movie>>> = flow {
        val initialData = movieDao.getMoviesByCategory("now_playing").first().map { it.toMovie() }
        emit(Resource.Loading(initialData))

        if (fetchFromRemote) {
            try {
                val remoteMovies = api.getNowPlayingMovies().results
                movieDao.updateMoviesByCategory("now_playing", remoteMovies.map { it.toMovieEntity("now_playing") })
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "Network error", initialData))
            }
        }
        movieDao.getMoviesByCategory("now_playing").collect { entities ->
            emit(Resource.Success(entities.map { it.toMovie() }))
        }
    }

    override fun getUpcomingMovies(fetchFromRemote: Boolean): Flow<Resource<List<Movie>>> = flow {
        val initialData = movieDao.getMoviesByCategory("upcoming").first().map { it.toMovie() }
        emit(Resource.Loading(initialData))

        if (fetchFromRemote) {
            try {
                val remoteMovies = api.getUpcomingMovies().results
                movieDao.updateMoviesByCategory("upcoming", remoteMovies.map { it.toMovieEntity("upcoming") })
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "Network error", initialData))
            }
        }
        movieDao.getMoviesByCategory("upcoming").collect { entities ->
            emit(Resource.Success(entities.map { it.toMovie() }))
        }
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetail {
        return api.getMovieDetails(movieId).toMovieDetail()
    }

    override suspend fun getMovieCredits(movieId: Int): List<Cast> {
        return api.getMovieCredits(movieId).cast.map { it.toCast() }
    }

    override suspend fun getMovieTrailer(movieId: Int): String? {
        val videos = api.getMovieVideos(movieId).results
        return videos.find { it.site == "YouTube" && it.type == "Trailer" }?.key
            ?: videos.find { it.site == "YouTube" }?.key
    }

    override fun getPopularTvShows(fetchFromRemote: Boolean): Flow<Resource<List<TvShow>>> = flow {
        val initialData = tvShowDao.getTvShowsByCategory("popular").first().map { it.toTvShow() }
        emit(Resource.Loading(initialData))

        if (fetchFromRemote) {
            try {
                val remoteTvShows = api.getPopularTvShows().results
                tvShowDao.updateTvShowsByCategory("popular", remoteTvShows.map { it.toTvShowEntity("popular") })
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "Network error", initialData))
            }
        }
        tvShowDao.getTvShowsByCategory("popular").collect { entities ->
            emit(Resource.Success(entities.map { it.toTvShow() }))
        }
    }

    override fun getTopRatedTvShows(fetchFromRemote: Boolean): Flow<Resource<List<TvShow>>> = flow {
        val initialData = tvShowDao.getTvShowsByCategory("top_rated").first().map { it.toTvShow() }
        emit(Resource.Loading(initialData))

        if (fetchFromRemote) {
            try {
                val remoteTvShows = api.getTopRatedTvShows().results
                tvShowDao.updateTvShowsByCategory("top_rated", remoteTvShows.map { it.toTvShowEntity("top_rated") })
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "Network error", initialData))
            }
        }
        tvShowDao.getTvShowsByCategory("top_rated").collect { entities ->
            emit(Resource.Success(entities.map { it.toTvShow() }))
        }
    }

    override fun getAiringTodayTvShows(fetchFromRemote: Boolean): Flow<Resource<List<TvShow>>> = flow {
        emit(Resource.Loading())
        var networkError: String? = null
        if (fetchFromRemote) {
            try {
                val remoteTvShows = api.getAiringTodayTvShows().results
                tvShowDao.deleteTvShowsByCategory("airing_today")
                tvShowDao.insertTvShows(remoteTvShows.map { it.toTvShowEntity("airing_today") })
            } catch (e: Exception) {
                networkError = e.localizedMessage ?: "Network error"
            }
        }
        tvShowDao.getTvShowsByCategory("airing_today").collect { entities ->
            val tvShows = entities.map { it.toTvShow() }
            if (networkError != null && tvShows.isEmpty()) {
                emit(Resource.Error(networkError))
            } else {
                emit(Resource.Success(tvShows))
            }
        }
    }

    override suspend fun getTvShowDetails(tvId: Int): TvShowDetail {
        return api.getTvShowDetails(tvId).toTvShowDetail()
    }

    override suspend fun getTvShowCredits(tvId: Int): List<Cast> {
        return api.getTvShowCredits(tvId).cast.map { it.toCast() }
    }

    override suspend fun getTvShowTrailer(tvId: Int): String? {
        val videos = api.getTvShowVideos(tvId).results
        return videos.find { it.site == "YouTube" && it.type == "Trailer" }?.key
            ?: videos.find { it.site == "YouTube" }?.key
    }

    override suspend fun getTvSeasonDetails(tvId: Int, seasonNumber: Int): Season {
        return api.getTvSeasonDetails(tvId, seasonNumber).toSeason()
    }

    override suspend fun getPersonDetails(personId: Int): PersonDetail {
        return api.getPersonDetails(personId).toPersonDetail()
    }

    override suspend fun getPersonCombinedCredits(personId: Int): List<CombinedCredit> {
        return api.getPersonCombinedCredits(personId).cast.map { it.toCombinedCredit() }
    }

    override suspend fun getMovieGenres(): List<Genre> {
        try {
            val remoteGenres = api.getMovieGenres().genres
            genreDao.deleteGenresByType("movie")
            genreDao.insertGenres(remoteGenres.map { it.toGenreEntity("movie") })
        } catch (e: Exception) {
        }
        return genreDao.getGenresByType("movie").map { it.toGenre() }
    }

    override suspend fun getTvGenres(): List<Genre> {
        try {
            val remoteGenres = api.getTvGenres().genres
            genreDao.deleteGenresByType("tv")
            genreDao.insertGenres(remoteGenres.map { it.toGenreEntity("tv") })
        } catch (e: Exception) {
        }
        return genreDao.getGenresByType("tv").map { it.toGenre() }
    }

    override fun discoverMoviesByGenre(genreId: Int, page: Int, fetchFromRemote: Boolean): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading())
        val category = "genre_$genreId"
        
        var networkError: String? = null
        if (fetchFromRemote) {
            try {
                val remoteMovies = api.discoverMoviesByGenre(genreId, page).results
                if (page == 1) {
                    movieDao.deleteMoviesByCategory(category)
                }
                movieDao.insertMovies(remoteMovies.map { it.toMovieEntity(category) })
            } catch (e: Exception) {
                networkError = e.localizedMessage ?: "Network error"
            }
        }
        
        movieDao.getMoviesByCategory(category).collect { entities ->
            val movies = entities.map { it.toMovie() }
            if (networkError != null && movies.isEmpty()) {
                emit(Resource.Error(networkError))
            } else {
                emit(Resource.Success(movies))
            }
        }
    }

    override fun discoverTvShowsByGenre(genreId: Int, page: Int, fetchFromRemote: Boolean): Flow<Resource<List<TvShow>>> = flow {
        emit(Resource.Loading())
        val category = "genre_$genreId"
        
        var networkError: String? = null
        if (fetchFromRemote) {
            try {
                val remoteTvShows = api.discoverTvShowsByGenre(genreId, page).results
                if (page == 1) {
                    tvShowDao.deleteTvShowsByCategory(category)
                }
                tvShowDao.insertTvShows(remoteTvShows.map { it.toTvShowEntity(category) })
            } catch (e: Exception) {
                networkError = e.localizedMessage ?: "Network error"
            }
        }
        
        tvShowDao.getTvShowsByCategory(category).collect { entities ->
            val tvShows = entities.map { it.toTvShow() }
            if (networkError != null && tvShows.isEmpty()) {
                emit(Resource.Error(networkError))
            } else {
                emit(Resource.Success(tvShows))
            }
        }
    }

    override suspend fun multiSearch(query: String, page: Int): List<SearchResult> {
        return api.multiSearch(query, page).results.map { dto ->
            SearchResult(
                id = dto.id,
                name = dto.name,
                title = dto.title,
                mediaType = dto.mediaType,
                posterPath = dto.posterPath,
                profilePath = dto.profilePath,
                backdropPath = dto.backdropPath,
                voteAverage = dto.voteAverage,
                releaseDate = dto.releaseDate,
                firstAirDate = dto.firstAirDate
            )
        }
    }

    override fun getAllFavorites(): Flow<List<Favorite>> {
        return favoriteDao.getAllFavorites().map { entities ->
            entities.map { it.toFavorite() }
        }
    }

    override suspend fun addFavorite(favorite: Favorite) {
        favoriteDao.insertFavorite(favorite.toFavoriteEntity())
    }

    override suspend fun removeFavorite(favorite: Favorite) {
        favoriteDao.deleteFavorite(favorite.toFavoriteEntity())
    }

    override suspend fun isFavorite(id: Int): Boolean {
        return favoriteDao.isFavorite(id)
    }
}
