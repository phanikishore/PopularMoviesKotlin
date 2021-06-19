package com.barun.bankopen.data.persistance

import androidx.room.*
import com.barun.bankopen.data.model.RouteTimingDB

@Dao
interface RouteTimingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(routeTiming: RouteTimingDB)

    @Transaction
    @Query("SELECT * FROM RouteTimingDB WHERE id = :routeId")
    fun fetchRouteTimings(routeId: String): RouteTimingDB

    @Query("SELECT * FROM RouteTimingDB")
    fun fetchAllRouteTimings(): List<RouteTimingDB>
}