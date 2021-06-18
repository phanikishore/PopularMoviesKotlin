package com.barun.bankopen.data.persistance

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase


abstract class RouteDatabase() : RoomDatabase() {
    private val mTAG = RouteDatabase::class.java.simpleName
}