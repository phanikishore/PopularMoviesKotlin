package com.barun.bankopen.data.persistance

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.barun.bankopen.data.model.RouteInfo

@Dao
interface RouteInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(routeInfo: RouteInfo)
}