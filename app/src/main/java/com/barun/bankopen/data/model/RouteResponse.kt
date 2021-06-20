package com.barun.bankopen.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RouteResponse(
    var routeInfo: MutableList<RouteInfo>? = null,
    @Serializable(with = JsonMapSerializer::class)
    var routeTimings: Map<String, List<RouteTiming>>? = null
)