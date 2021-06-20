package com.barun.bankopen.data.persistance

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.barun.bankopen.data.model.RouteTimingDB
import kotlinx.coroutines.flow.Flow

@Dao
interface RouteTimingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(routeTiming: RouteTimingDB)

    @Query("SELECT * FROM RouteTimingDB")
    fun fetchAllRouteTimingsAsFlow(): Flow<List<RouteTimingDB>>
}