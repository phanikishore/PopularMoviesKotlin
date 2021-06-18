package com.barun.bankopen.data.persistance

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.barun.bankopen.data.model.RouteTimingDB

@Dao
interface RouteTimingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(routeTiming: RouteTimingDB)
}