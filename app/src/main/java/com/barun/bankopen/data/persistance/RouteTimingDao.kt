package com.barun.bankopen.data.persistance

import androidx.room.*
import com.barun.bankopen.data.model.RouteTiming
import com.barun.bankopen.data.model.RouteTimingDB
import kotlinx.coroutines.flow.Flow

@Dao
interface RouteTimingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(routeTiming: RouteTimingDB)

    @Transaction
    @Query("SELECT * FROM RouteTimingDB WHERE id = :routeId")
    fun fetchRouteTimings(routeId: String):Flow<RouteTimingDB>
}