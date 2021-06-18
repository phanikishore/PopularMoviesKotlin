package com.kishore.tmdbapp.data.repository

import android.util.Log
import com.kishore.tmdbapp.data.model.Configuration
import com.kishore.tmdbapp.data.model.DashBoardResponse
import com.kishore.tmdbapp.data.model.MoviesResponse
import com.kishore.tmdbapp.data.model.RouteResponse
import com.kishore.tmdbapp.data.network.ApiService
import com.kishore.tmdbapp.data.network.TmdbApi
import com.kishore.tmdbapp.data.utils.ApiResponse
import kotlinx.coroutines.*
import kotlinx.serialization.ExperimentalSerializationApi
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@ExperimentalSerializationApi
class TmdbRepository @Inject constructor(private var apiService: TmdbApi?) {
    val mTAG = TmdbRepository::class.java.simpleName
    val coroutineErrorHandler = CoroutineExceptionHandler { _, exception ->
        Log.e(mTAG, exception.printStackTrace().toString())
    }

    //Fetch Configuration Info
    suspend fun fetchConfiguration(): Configuration? {
        return apiService?.fetchConfiguration()
    }

    //DashBoard Response (Popular + Top Movies) together
    suspend fun fetchDashBoardResponse(): DashBoardResponse {
        val response = DashBoardResponse()
        try {
            CoroutineScope(Dispatchers.IO + coroutineErrorHandler).launch {
                val job1 = async {
                    apiService?.getPopularMovies(1)
                }
                val job2 = async {
                    apiService?.getTopRatedMovies(1)
                }
                response.popularMovies = job1.await()
                response.topMovies = job2.await()
            }.join()
        } catch (e: HttpException) {
            e.printStackTrace()
        }
        return response
    }

    suspend fun fetchPopularMovies(pageNo: Int): MoviesResponse? {
        return apiService?.getPopularMovies(pageNo)
    }

    suspend fun fetchTopMovies(pageNo: Int): MoviesResponse? {
        return apiService?.getTopRatedMovies(pageNo)
    }

    suspend fun fetchLatestMovies(): MoviesResponse? {
        return apiService?.getLatestMovies()
    }

    suspend fun fetchUpComingMovies(pageNo: Int):MoviesResponse?{
        return apiService?.getUpComingMovies(pageNo)
    }

    suspend fun fetchPopularTvShows(pageNo: Int):MoviesResponse?{
        return apiService?.getTvPopularShows(pageNo)
    }

    suspend fun fetchRouteResponse():ApiResponse<RouteResponse?>{
        return ApiResponse.Success<RouteResponse>(apiService?.getRouteResponse() as RouteResponse)
    }
}
