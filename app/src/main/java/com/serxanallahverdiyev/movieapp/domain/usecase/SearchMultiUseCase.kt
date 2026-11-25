package com.serxanallahverdiyev.movieapp.domain.usecase

import com.serxanallahverdiyev.movieapp.core.common.Resource
import com.serxanallahverdiyev.movieapp.domain.model.SearchResult
import com.serxanallahverdiyev.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchMultiUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(query: String, page: Int = 1): Flow<Resource<List<SearchResult>>> = flow {
        if (query.isBlank()) {
            emit(Resource.Success(emptyList()))
            return@flow
        }
        
        try {
            emit(Resource.Loading())
            val results = repository.multiSearch(query, page)
            emit(Resource.Success(results))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}
