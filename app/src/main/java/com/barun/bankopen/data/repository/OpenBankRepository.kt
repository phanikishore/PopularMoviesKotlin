package com.barun.bankopen.data.repository

import android.util.Log
import com.barun.bankopen.data.model.RouteInfo
import com.barun.bankopen.data.model.RouteResponse
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
class OpenBankRepository @Inject constructor(private var apiService: OpenBankApi) {
    val TAG = OpenBankRepository::class.java.simpleName

    val coroutineErrorHandler = CoroutineExceptionHandler { _, exception ->
        Log.e(TAG, exception.printStackTrace().toString())
    }


    fun fetchRoute(): Flow<RouteResponse?> {
        return flow {
            Log.d(TAG, "Before Thread${Thread.currentThread().name}")
            val response = apiService.getLatestRoutes()
            emit(response)
            Log.d(TAG, "After Thread${Thread.currentThread().name}")
        }.flowOn(Dispatchers.IO)
    }

    fun insertRouteInfo(routeInfo: RouteInfo){

    }
}
