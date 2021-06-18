package com.barun.bankopen.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RouteTiming(
    val avaiable: Int,
    val totalSeats: Int,
    val tripStartTime: String
)