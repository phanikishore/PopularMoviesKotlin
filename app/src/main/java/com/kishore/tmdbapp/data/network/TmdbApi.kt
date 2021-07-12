package com.kishore.tmdbapp.data.network

import com.kishore.tmdbapp.data.model.Configuration
import com.kishore.tmdbapp.data.model.MoviesResponse
import com.kishore.tmdbapp.data.utils.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface TmdbApi {

    @GET("configuration")
    suspend fun fetchConfiguration(): Configuration

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") pageNo: Int): MoviesResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("page") pageNo: Int): MoviesResponse

    @GET("/movie/upcoming")
    suspend fun getUpComingMovies(@Query("page") pageNo: Int): MoviesResponse

    @GET("/movie/latest")
    suspend fun getLatestMovies(): MoviesResponse

    @GET("/tv/popular")
    suspend fun getTvPopularShows(@Query("page") pageNo: Int): MoviesResponse
}