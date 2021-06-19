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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.ExperimentalSerializationApi
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

    suspend fun fetchRoutesFromDB(currentTime: Int): Flow<RouteResponse?> {
        /*val value = CoroutineScope(Dispatchers.Main).async {
            val map = hashMapOf<String, List<RouteTiming>>()
            Log.d(TAG, "Before fetchRoutesFromDB:${Thread.currentThread().name}")
            val routeResponse = RouteResponse()
            dbService.routeInfoDao.fetchRoutes().observeForever { listRouteInfo ->
                val listRouteTimings = dbService.routeTimingDao.fetchAllRouteTimings()
                routeResponse.apply {
                    routeInfo = listRouteInfo
                }
                listRouteTimings.forEach { routeTimingDb ->
                    routeTimingDb.listTimings.filter {
                        it.tripStartTime.convertToMinutes() >= 900
                    }.also { futureRouteTiming ->
                        map[routeTimingDb.id] = futureRouteTiming
                    }
                }
            }
            routeResponse.apply {
                routeTimings = map
            }
        }
        return value.await()*/
        return flow {
            val routeResponse = RouteResponse()
            val map = hashMapOf<String, List<RouteTiming>>()
            val listRouteInfoFlow = dbService.routeInfoDao.fetchRoutes()
            listRouteInfoFlow.collect { routeInfoList->
                emit(routeResponse.apply {
                    routeInfo = routeInfoList
                    val listRouteTimings = dbService.routeTimingDao.fetchAllRouteTimings()
                    listRouteTimings.forEach { routeTimingDb ->
                        routeTimingDb.listTimings.filter {
                            it.tripStartTime.convertToMinutes() >= 900
                        }.also { futureRouteTiming ->
                            map[routeTimingDb.id] = futureRouteTiming
                        }
                    }
                    routeTimings = map
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
