package com.barun.bankopen.data.persistance

import androidx.room.*
import com.barun.bankopen.data.model.RouteInfo

@Dao
interface RouteInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg routeInfo: RouteInfo)

    @Transaction
    @Query("SELECT * FROM RouteInfo WHERE id = :id")
    fun fetchRouteInfo(id:String): RouteInfo
}