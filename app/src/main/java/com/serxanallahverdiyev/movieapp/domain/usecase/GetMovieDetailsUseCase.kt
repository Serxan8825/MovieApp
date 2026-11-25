package com.serxanallahverdiyev.movieapp.domain.usecase

import com.serxanallahverdiyev.movieapp.core.common.Resource
import com.serxanallahverdiyev.movieapp.domain.model.Cast
import com.serxanallahverdiyev.movieapp.domain.model.MovieDetail
import com.serxanallahverdiyev.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

data class MovieFullDetail(
    val detail: MovieDetail,
    val cast: List<Cast>,
    val trailerUrl: String?,
    val isFavorite: Boolean
)

class GetMovieDetailsUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(movieId: Int): Flow<Resource<MovieFullDetail>> = flow {
        try {
            emit(Resource.Loading())
            
            val detail = repository.getMovieDetails(movieId)
            val cast = repository.getMovieCredits(movieId)
            val trailerKey = repository.getMovieTrailer(movieId)
            val isFavorite = repository.isFavorite(movieId)
            
            val fullDetail = MovieFullDetail(
                detail = detail,
                cast = cast,
                trailerUrl = trailerKey,
                isFavorite = isFavorite
            )
            
            emit(Resource.Success(fullDetail))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}
