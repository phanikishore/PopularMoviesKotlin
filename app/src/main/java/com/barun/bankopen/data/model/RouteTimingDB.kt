package com.barun.bankopen.data.model

import androidx.room.*
import com.barun.bankopen.data.persistance.RouteTimingConverter
import java.sql.RowId

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
    val rowId: Int=0
)