package com.barun.bankopen.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable


@Serializable
@Entity
data class RouteInfo(
    val destination: String,
    val icon: String,
    @PrimaryKey()
    val id: String,
    val name: String,
    val source: String,
    val tripDuration: String
)