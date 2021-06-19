package com.barun.bankopen.ui.dashboard

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barun.bankopen.data.model.RouteInfo
import com.barun.bankopen.data.model.RouteResponse
import com.barun.bankopen.data.model.RouteTiming
import com.barun.bankopen.data.repository.OpenBankRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
@HiltViewModel
class DashBoardViewModel @Inject constructor(val repository: OpenBankRepository) : ViewModel() {

    private val TAG = DashBoardViewModel::class.java.simpleName
    var routeInfoLiveData: MutableLiveData<RouteResponse> = MutableLiveData()


    fun fetchRouteInfo(timeInMins: Int) {
        Log.d(TAG, "timeInMins:$timeInMins")
        viewModelScope.launch {
            Log.d(TAG, "1 fetchRouteInfo:${Thread.currentThread().name}")
            val response = repository.fetchRoutesFromDB(timeInMins)
            response.collect {
                routeInfoLiveData.value = it
                Log.d(TAG, "2 fetchRouteInfo:${Thread.currentThread().name}")
                Log.d(TAG, "response::$it")
            }
        }
    }
}

