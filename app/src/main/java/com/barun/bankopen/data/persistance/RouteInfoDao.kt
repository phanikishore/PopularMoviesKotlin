package com.barun.bankopen.data.persistance

import androidx.room.*
import com.barun.bankopen.data.model.RouteInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface RouteInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg routeInfo: RouteInfo)

    @Transaction
    @Query("SELECT * FROM RouteInfo")
    fun fetchRoutes(): Flow<List<RouteInfo>>
}