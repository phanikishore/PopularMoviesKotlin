package com.barun.bankopen.data.model

import androidx.room.*
import com.barun.bankopen.data.persistance.RouteTimingConverter
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
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
    @ColumnInfo(index = true)
    val id: String,
    val listTimings: List<RouteTiming>,
    @PrimaryKey(autoGenerate = true)
    @Transient
    val rowId: Int = 0
)