package com.lebartodev.core.network

import com.lebartodev.lib.data.network.CreditsResponse
import com.lebartodev.lib.data.network.MovieResponse
import com.lebartodev.lib.data.network.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int
    ): MoviesResponse

    @GET("movie/now_playing")
    suspend fun getNowPlaying(): MoviesResponse

    @GET("movie/latest")
    suspend fun getLatest(): MoviesResponse

    @GET("movie/popular")
    suspend fun getPopular(): MoviesResponse

    @GET("movie/top_rated")
    suspend fun getTopRated(): MoviesResponse

    @GET("movie/upcoming")
    suspend fun getUpcoming(): MoviesResponse

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(@Path("movieId") movieId: Long): MovieResponse

    @GET("movie/{movieId}/credits")
    suspend fun getMovieCredits(@Path("movieId") movieId: Long): CreditsResponse
}