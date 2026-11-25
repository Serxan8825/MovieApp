package com.serxanallahverdiyev.movieapp.domain.usecase

import com.serxanallahverdiyev.movieapp.core.common.Resource
import com.serxanallahverdiyev.movieapp.domain.model.Cast
import com.serxanallahverdiyev.movieapp.domain.model.TvShowDetail
import com.serxanallahverdiyev.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

data class TvShowFullDetail(
    val detail: TvShowDetail,
    val cast: List<Cast>,
    val trailerUrl: String?,
    val isFavorite: Boolean
)

class GetTvShowDetailsUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(tvId: Int): Flow<Resource<TvShowFullDetail>> = flow {
        try {
            emit(Resource.Loading())
            
            val detail = repository.getTvShowDetails(tvId)
            val cast = repository.getTvShowCredits(tvId)
            val trailerKey = repository.getTvShowTrailer(tvId)
            val isFavorite = repository.isFavorite(tvId)
            
            val fullDetail = TvShowFullDetail(
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
