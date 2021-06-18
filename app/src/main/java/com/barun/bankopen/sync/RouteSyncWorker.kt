package com.barun.bankopen.sync

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.barun.bankopen.data.repository.OpenBankRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@HiltWorker
class RouteSyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    val openBankRepository: OpenBankRepository
) : CoroutineWorker(appContext, workerParams) {

    private val TAG = RouteSyncWorker::class.java.canonicalName

    override suspend fun doWork(): Result {
        Log.d(TAG, "1 Thread${Thread.currentThread().name}")
        openBankRepository.fetchRouteFromNetwork().catch {
        }.collect { routeResponse->
            val routeInfo = routeResponse?.routeInfo
            val routeTiming = routeResponse?.routeTimings
            Log.d(TAG, "collected data routeInfo:$routeInfo")
            Log.d(TAG, "collected data routeTiming:${routeTiming?.get("r003")}")
            openBankRepository.persistRouteData(routeResponse)
        }
        return Result.success()
    }
}
