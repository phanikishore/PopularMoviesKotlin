package com.kishore.tmdbapp.data.repository

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.kishore.tmdbapp.BuildConfig
import com.kishore.tmdbapp.data.network.TmdbApi
import com.kishore.tmdbapp.data.utils.ApiResponse
import com.kishore.tmdbapp.utils.isNetworkAvailable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    private val mediaType = "application/json".toMediaType()
    private const val APIKEY = "api_key"

    @Provides
    @Singleton
    fun provideTmdbRepo(apiService: TmdbApi?): TmdbRepository {
        return TmdbRepository(apiService)
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): TmdbApi? {
        return retrofit.create(TmdbApi::class.java)
    }

    @Provides
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory(mediaType))
            .build()
    }

    /***
     * Create OkHttpClient Instance
     */
    @Provides
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(loggerInterceptor)
            addInterceptor(addAPIKeyInterceptor)
            addInterceptor(removeAPIKeyInterceptor)
            addInterceptor(internetConnectivityInterceptor(context))
        }.build()
    }

    /***
     * Create HttpLoggingInterceptor Instance with Level.BODY
     */
    private val loggerInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    /***
     * Add API Key to Request Body
     */
    private val addAPIKeyInterceptor = Interceptor { chain ->
        var originalRequest = chain.request()
        val newUrl = originalRequest.url.newBuilder().apply {
            addQueryParameter(APIKEY, BuildConfig.API_KEY)
        }
        originalRequest = originalRequest.newBuilder().url(newUrl.build()).build()
        chain.proceed(originalRequest)
    }


    /***
     * Remove API Key from Request Body
     */
    private val removeAPIKeyInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        var originalResponse = chain.proceed(chain.request())
        val newUrl = originalRequest.url.newBuilder().apply {
            removeAllQueryParameters(APIKEY)
        }
        originalResponse = originalResponse.newBuilder().apply {
            request(originalRequest.newBuilder().url(newUrl.build()).build())
        }.build()
        originalResponse
    }

    /***
     * Add Network Connectivity Check Interceptor
     */
    private fun internetConnectivityInterceptor(@ApplicationContext context: Context) =
        Interceptor { chain ->
            when (context.isNetworkAvailable()) {
                true -> {
                    chain.proceed(chain.request())
                }
                false -> {
                    val responseBody = "No Internet Available.".toResponseBody(mediaType)
                    Response.Builder().body(responseBody)
                        .code(12023)
                        .message("No Internet Available")
                        .request(chain.request())
                        .protocol(Protocol.HTTP_2)
                        .build()
                    //throw Exception("No Internet Available.")
                }
            }
        }


}