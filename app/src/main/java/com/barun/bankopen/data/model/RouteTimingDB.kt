package com.barun.bankopen.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.TypeConverters
import com.barun.bankopen.data.persistance.RouteTimingConverter

@Entity(
    foreignKeys = [ForeignKey(
        entity = RouteInfo::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("id"),
        onDelete = ForeignKey.CASCADE
    )]
)
@TypeConverters(RouteTimingConverter::class)
class RouteTimingDB(
    val id: String,
    val listTimings: List<RouteTiming>
)