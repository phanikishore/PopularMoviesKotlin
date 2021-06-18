package com.barun.bankopen.ui.dashboard

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barun.bankopen.data.model.RouteInfo
import com.barun.bankopen.data.model.RouteTiming
import com.barun.bankopen.data.repository.OpenBankRepository
import com.barun.bankopen.utils.convertToMinutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
@HiltViewModel
class DashBoardViewModel @Inject constructor(val repository: OpenBankRepository) : ViewModel() {

    private val TAG = DashBoardViewModel::class.java.simpleName
    private var map = hashMapOf<RouteInfo, List<RouteTiming>?>()
    var routeInfoLiveData: MutableLiveData<HashMap<RouteInfo, List<RouteTiming>?>> = MutableLiveData()


    fun fetchRouteInfo(timeInMins: Int) {
        viewModelScope.launch {
            repository.fetchRoutesFromDB().catch {
                Log.d(TAG, "Exception")
                routeInfoLiveData.value = null
            }.map { list ->
                list?.forEach { routeInfo ->
                    repository.fetchRouteTimings(routeInfo.id).collect { routeTiming ->
                        Log.d(TAG, Thread.currentThread().name)
                        /*routeTiming?.let { routeTimingDB ->
                            routeTimingDB.listTimings.forEach { timing ->
                                timing.apply {
                                    tripStartTimeInMins = tripStartTime.convertToMinutes()
                                }
                            }
                            routeTimingDB.listTimings.filter {
                                it.tripStartTimeInMins >= timeInMins
                            }.also {
                                map[routeInfo] = it
                            }
                        }*/
                        map[routeInfo] = routeTiming?.listTimings
                    }
                }
                Log.d(TAG,"Map-----> ${map}")
                routeInfoLiveData.value = map
            }
        }
    }
}

