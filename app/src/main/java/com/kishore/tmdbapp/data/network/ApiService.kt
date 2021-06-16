package com.kishore.tmdbapp.data.network

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Inject

@ExperimentalSerializationApi
class ApiService {
    private val mTAG = ApiService::class.java.simpleName
    private val baseUrl = "https://api.themoviedb.org/3/"
    private val mediaType = "application/json".toMediaType()
    private val apikey = "a3d203c426fb20f97a4ce8f3fd601e32"
    private var mInstance: TmdbApi? = null

    /***
     * create Retrofit Instance
     */
    private fun buildRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(createOkHttpClient())
            .addConverterFactory(Json.asConverterFactory(mediaType))
            .build()
    }

    /***
     * Build ApiService Instance
     */
    @Synchronized
    fun getService(): TmdbApi? {
        Log.i(mTAG, "is mInstance NULL? ${mInstance == null}")
        return mInstance ?: synchronized(this) {
            mInstance = buildRetrofitInstance().create(TmdbApi::class.java)
            mInstance
        }
    }

    /***
     * Create OkHttpClient Instance
     */
    private fun createOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor(loggerInterceptor())
        okHttpClient.addInterceptor(addAPIKeyInterceptor())
        okHttpClient.addInterceptor(removeAPIKeyInterceptor())
        return okHttpClient.build()
    }

    /***
     * Create HttpLoggingInterceptor Instance with Level.BODY
     */
    private fun loggerInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    /***
     * Add API Key to Request Body
     */
    private fun addAPIKeyInterceptor(): Interceptor {
        return Interceptor { chain ->
            var originalRequest = chain.request()
            val newUrl = originalRequest.url.newBuilder().apply {
                addQueryParameter("api_key", apikey)
            }
            originalRequest = originalRequest.newBuilder().url(newUrl.build()).build()
            chain.proceed(originalRequest)
        }
    }

    /***
     * Remove API Key from Request Body
     */
    private fun removeAPIKeyInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            var originalResponse = chain.proceed(chain.request())
            val newUrl = originalRequest.url.newBuilder().apply {
                removeAllQueryParameters("api_key")
            }
            originalResponse = originalResponse.newBuilder().apply {
                request(originalRequest.newBuilder().url(newUrl.build()).build())
            }.build()
            originalResponse
        }
    }

    fun getAPIKey(): String {
        return apikey
    }
}