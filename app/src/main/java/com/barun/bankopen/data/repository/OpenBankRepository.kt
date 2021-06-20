package com.barun.bankopen.data.repository

import android.util.Log
import com.barun.bankopen.data.model.RouteInfo
import com.barun.bankopen.data.model.RouteResponse
import com.barun.bankopen.data.model.RouteTiming
import com.barun.bankopen.data.model.RouteTimingDB
import com.barun.bankopen.data.persistance.RouteDBRepo
import com.barun.bankopen.utils.convertToMinutes
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.ExperimentalSerializationApi
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@ExperimentalSerializationApi
class OpenBankRepository @Inject constructor(
    private var apiService: OpenBankApi,
    private var dbService: RouteDBRepo
) {

    val TAG = OpenBankRepository::class.java.simpleName

    val coroutineErrorHandler = CoroutineExceptionHandler { _, exception ->
        Log.e(TAG, exception.printStackTrace().toString())
    }

    fun fetchRouteFromNetwork(): Flow<RouteResponse?> {
        return flow {
            Log.d(TAG, "Before Thread${Thread.currentThread().name}")
            val response = apiService.getLatestRoutes()
            emit(response)
            Log.d(TAG, "After Thread${Thread.currentThread().name}")
        }.flowOn(Dispatchers.IO)
    }

    suspend fun fetchRoutesFromDB(): Flow<RouteResponse?> {
        return flow {
            val routeResponse = RouteResponse()
            val map = hashMapOf<String, List<RouteTiming>>()
            val listRouteTimingsFlow = dbService.routeTimingDao.fetchAllRouteTimingsAsFlow()
            listRouteTimingsFlow.collect { routeTimings ->
                delay(1000)
                val sdf = SimpleDateFormat("HH:mm", Locale.ENGLISH)
                val currentTime = sdf.format(Date()).convertToMinutes()
                emit(routeResponse.apply {
                    this.routeInfo = mutableListOf()
                    routeTimings.forEach { routeTimingDb ->
                        //Perform RouteTiming filter based on currentTime
                        routeTimingDb.listTimings.filter {
                            it.tripStartTime.convertToMinutes() > currentTime
                        }.also { futureRouteTiming ->
                            if (futureRouteTiming.isNotEmpty()) {
                                //Perform Sorting for all the RouteTimings on Individual RouteInfo Id
                                futureRouteTiming.sortedBy { it.tripStartTime.convertToMinutes() }
                                    .also {
                                        //Collect into a map with routeId as Key and RouteTiming as Value
                                        map[routeTimingDb.id] = it
                                    }
                            }
                        }
                    }
                    //Perform sorting order on keys with respect to trip start Time of RouteTiming.
                    map.toList().sortedBy {
                        it.second[0].tripStartTime.convertToMinutes()
                    }.also { sortedListOfMap ->
                        //Collect RouteInfo Object from Database based on sorted List of Map.
                        sortedListOfMap.forEach { pair ->
                            this.routeInfo?.add(
                                dbService.routeInfoDao.fetchRouteInfo(
                                    pair.first
                                )
                            )
                        }
                        this.routeTimings = sortedListOfMap.toMap()
                    }
                })
            }
        }.flowOn(Dispatchers.IO)

    }

    private fun insertRouteInfo(routeInfo: List<RouteInfo>?) {
        routeInfo?.let { dbService.routeInfoDao.insert(*it.toTypedArray()) }
    }

    private fun insertRouteTimings(routeTiming: Map<String, List<RouteTiming>>?) {
        routeTiming?.forEach { (key, values) ->
            dbService.routeTimingDao.insert(RouteTimingDB(id = key, listTimings = values))
        }
    }

    fun persistRouteData(routeResponse: RouteResponse?) {
        insertRouteInfo(routeInfo = routeResponse?.routeInfo)
        insertRouteTimings(routeTiming = routeResponse?.routeTimings)
    }
}
