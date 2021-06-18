package com.barun.bankopen.data.persistance

import androidx.room.Database
import androidx.room.RoomDatabase
import com.barun.bankopen.data.model.RouteInfo
import com.barun.bankopen.data.model.RouteTimingDB

@Database(entities = [RouteInfo::class, RouteTimingDB::class], version = 1)
abstract class RouteDatabase() : RoomDatabase() {
    private val mTAG = RouteDatabase::class.java.simpleName
    abstract fun routeInfoDao(): RouteInfoDao
    abstract fun routeTimingDao(): RouteTimingDao
}