package com.barun.bankopen.ui.dashboard

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barun.bankopen.data.model.RouteResponse
import com.barun.bankopen.data.repository.OpenBankRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
@HiltViewModel
class DashBoardViewModel @Inject constructor(val repository: OpenBankRepository) : ViewModel() {

    private val TAG = DashBoardViewModel::class.java.simpleName
    var routeListLiveData: MutableLiveData<RouteResponse> = MutableLiveData()


    fun fetRouteTimings() {
        viewModelScope.launch {
            repository.fetchRoute().catch {
                Log.d(TAG, "Exception")
                routeListLiveData.value = null
            }.collect {
                routeListLiveData.value = it
            }
        }
    }
}

