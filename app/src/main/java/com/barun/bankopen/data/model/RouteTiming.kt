package com.barun.bankopen.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class RouteTiming(
    val avaiable: Int,
    val totalSeats: Int,
    val tripStartTime: String,
    /*@Transient
    var tripStartTimeInMins: Int = 0*/
)