package com.lebartodev.feature_artist.repository

import com.lebartodev.core.db.dao.CreditsDao
import com.lebartodev.core.network.AsyncResult
import com.lebartodev.core.network.MoviesService
import com.lebartodev.lib.data.entity.PersonEntity
import com.lebartodev.lib.data.mapper.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ArtistRepositoryImpl @Inject constructor(
    private val service: MoviesService,
    private val creditsDao: CreditsDao
) : ArtistRepository {
    override fun getArtistDetails(creditId: String): Flow<AsyncResult<PersonEntity>> {
        return flow {
            val creditDetails = service.getCreditDetails(creditId = creditId)
            emit(AsyncResult.Loading())
            val personId = creditDetails.person?.id
            if (personId == null) {
                emit(AsyncResult.Error(NullPointerException()))
                return@flow
            }
            val person = service.getPerson(personId)
            //creditsDao.upsertPerson
            emit(AsyncResult.Success(person.toEntity()))
        }
    }
}