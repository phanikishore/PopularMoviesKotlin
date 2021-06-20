package com.barun.bankopen.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.barun.bankopen.data.model.RouteInfo
import com.barun.bankopen.data.model.RouteResponse
import com.barun.bankopen.data.model.RouteTiming
import com.barun.bankopen.databinding.RouteListItemBinding
import com.barun.bankopen.utils.convertToMinutes

class RouteListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = mutableMapOf<String, List<RouteTiming>?>()
    private var routeList = mutableListOf<RouteInfo>()
    private var deviceCurrentTime: Int = 0
    fun addItems(routeResponse: RouteResponse, currentTime: Int = 0) {
        routeResponse.routeTimings?.let {
            items = it.toMutableMap()
        }
        routeResponse.routeInfo?.let {
            routeList = it
        }
        deviceCurrentTime = currentTime
        notifyItemRangeChanged(0, routeList.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding =
            RouteListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            with(holder) { bind(Pair(routeList[position], items[routeList[position].id])) }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ItemViewHolder(val item: RouteListItemBinding) :
        RecyclerView.ViewHolder(item.root) {
        fun bind(data: Pair<RouteInfo, List<RouteTiming>?>) {
            val routeInfo = data.first
            val routeTiming = data.second
            item.textViewOrigin.text = routeInfo.source
            item.textViewDestination.text = routeInfo.destination
            ("Travel Time: " + routeInfo.tripDuration).also { item.tripDurationTextView.text = it }
            if (routeTiming?.isEmpty() != true) {
                item.textViewStartTime.text = routeTiming?.get(0)?.tripStartTime
                item.departureInTextview.text = calculateDepartureTime(routeInfo, routeTiming)
                if (routeInfo.tripDuration.contains("hrs")) {
                    val time = routeInfo.tripDuration.dropLast(3)
                    val tripStartTimeMin = routeTiming?.get(0)?.tripStartTime?.convertToMinutes()
                    tripStartTimeMin?.let { setArrivalTimeData(time.toInt() * 60, it) }
                }
                if (routeInfo.tripDuration.contains(" min")) {
                    val time = routeInfo.tripDuration.dropLast(3)
                    val tripStartTimeMin = routeTiming?.get(0)?.tripStartTime?.convertToMinutes()
                    tripStartTimeMin?.let { setArrivalTimeData(time.trim().toInt(), it) }
                }
            }
        }

        private fun calculateDepartureTime(
            routeInfo: RouteInfo,
            routeTiming: List<RouteTiming>?
        ): String {
            val departInMin =
                routeTiming?.get(0)?.tripStartTime?.convertToMinutes()?.let {
                    it - deviceCurrentTime
                }
            val timeInPair = Pair(departInMin?.div(60), departInMin?.rem(60))
            var durationInString = ""
            timeInPair.second?.let { minutes ->
                if (minutes >= 0) {
                    durationInString = "${timeInPair.second} min"
                }
            }
            timeInPair.first?.let { hours ->
                if (hours > 0) {
                    durationInString = "${timeInPair.first} hr $durationInString"
                }
            }
            return durationInString
        }

        private fun setArrivalTimeData(arrivalTime: Int, tripStartTime: Int) {
            val arrivalTimeMins = tripStartTime + (arrivalTime)
            val arrivalTimePair = Pair(arrivalTimeMins / 60, arrivalTimeMins % 60)
            "${arrivalTimePair.first}:${arrivalTimePair.second}".also {
                item.textViewEndTime.text = it
            }
        }
    }
}