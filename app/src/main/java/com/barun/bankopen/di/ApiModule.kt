package com.barun.bankopen.di

import android.content.Context
import androidx.room.Room
import com.barun.bankopen.BuildConfig
import com.barun.bankopen.data.persistance.RouteDBRepo
import com.barun.bankopen.data.persistance.RouteDatabase
import com.barun.bankopen.data.repository.OpenBankApi
import com.barun.bankopen.data.repository.OpenBankRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private val mediaType = "application/json".toMediaType()

    @Provides
    @Singleton
    fun provideRepo(apiService: OpenBankApi, database: RouteDBRepo): OpenBankRepository {
        return OpenBankRepository(apiService, database)
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): OpenBankApi {
        return retrofit.create(OpenBankApi::class.java)
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
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(loggerInterceptor)
        }.build()
    }

    /***
     * Create HttpLoggingInterceptor Instance with Level.BODY
     */
    private val loggerInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

}