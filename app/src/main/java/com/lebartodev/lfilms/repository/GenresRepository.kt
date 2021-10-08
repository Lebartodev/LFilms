package com.lebartodev.lfilms.repository

//
//import com.lebartodev.core.db.DatabaseProvider
//import com.lebartodev.core.db.entity.toGenreEntities
//import com.lebartodev.core.network.LFilmsNetwork
//
//class GenresRepository {
//    suspend fun fetchGenres() {
//        val genres = LFilmsNetwork.apiService.getGenres(LFilmsNetwork.API_KEY)
//        com.lebartodev.core.db.DatabaseProvider.database.genresDao().upsertGenres(genres.toGenreEntities())
//    }
//
//
//}