package com.lebartodev.feature_artist.repository

import com.lebartodev.core.network.AsyncResult
import com.lebartodev.lib.data.entity.PersonEntity
import kotlinx.coroutines.flow.Flow

interface ArtistRepository {
    fun getArtistDetails(creditId: String): Flow<AsyncResult<PersonEntity>>
}