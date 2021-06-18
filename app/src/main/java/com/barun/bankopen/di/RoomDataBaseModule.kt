package com.barun.bankopen.di

import android.content.Context
import androidx.room.Room
import com.barun.bankopen.data.persistance.RouteDBRepo
import com.barun.bankopen.data.persistance.RouteDatabase
import com.barun.bankopen.data.persistance.RouteInfoDao
import com.barun.bankopen.data.persistance.RouteTimingDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Singleton

@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
object RoomDataBaseModule {

    @Singleton
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        RouteDatabase::class.java,
        "route.db"
    ).build()

    @Provides
    @Singleton
    fun provideRouteDBRepo(routeInfoDao: RouteInfoDao,routeTimingDao: RouteTimingDao): RouteDBRepo{
        return RouteDBRepo(routeInfoDao, routeTimingDao)
    }

    @Provides
    @Singleton
    fun provideRouteInfoDao(db: RouteDatabase): RouteInfoDao = db.routeInfoDao()

    @Provides
    @Singleton
    fun provideRouteTimingDao(db: RouteDatabase): RouteTimingDao = db.routeTimingDao()
}