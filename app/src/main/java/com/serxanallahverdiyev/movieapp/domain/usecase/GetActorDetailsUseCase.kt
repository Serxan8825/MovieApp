package com.serxanallahverdiyev.movieapp.domain.usecase

import com.serxanallahverdiyev.movieapp.core.common.Resource
import com.serxanallahverdiyev.movieapp.domain.model.CombinedCredit
import com.serxanallahverdiyev.movieapp.domain.model.PersonDetail
import com.serxanallahverdiyev.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

data class ActorFullDetail(
    val detail: PersonDetail,
    val credits: List<CombinedCredit>
)

class GetActorDetailsUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(personId: Int): Flow<Resource<ActorFullDetail>> = flow {
        try {
            emit(Resource.Loading())
            val detail = repository.getPersonDetails(personId)
            val credits = repository.getPersonCombinedCredits(personId)
            emit(Resource.Success(ActorFullDetail(detail, credits)))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}
