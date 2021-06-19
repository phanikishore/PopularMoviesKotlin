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

    private var items = mapOf<String, List<RouteTiming>?>()
    private var routeList = listOf<RouteInfo>()
    private var deviceCurrentTime: Int = 0
    fun addItems(routeResponse: RouteResponse, currentTime: Int = 0) {
        routeResponse.routeTimings?.let {
            items = it
        }
        routeResponse.routeInfo?.let {
            routeList = it
        }
        deviceCurrentTime = currentTime
        notifyDataSetChanged()
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
            ("Travel Time:" + routeInfo.tripDuration).also { item.tripDurationTextView.text = it }
            if (routeTiming?.isEmpty() != true) {
                item.textViewStartTime.text = routeTiming?.get(0)?.tripStartTime
                val departInMin =
                    routeTiming?.get(0)?.tripStartTime?.convertToMinutes()!! - deviceCurrentTime
                val timeInPair = Pair(departInMin / 60, departInMin % 60)
                "${timeInPair.first}hr${timeInPair.second}min".also {
                    item.departureInTextview.text = it
                }
                if (routeInfo.tripDuration.contains("hrs")) {
                    val time = routeInfo.tripDuration.dropLast(3)
                    val tripStartTimeMin = routeTiming[0].tripStartTime.convertToMinutes()
                    setArrivalTimeData(time.toInt()*60,tripStartTimeMin)
                }
                if (routeInfo.tripDuration.contains(" min")) {
                    val time = routeInfo.tripDuration.dropLast(3)
                    val tripStartTimeMin = routeTiming[0].tripStartTime.convertToMinutes()
                    setArrivalTimeData(time.trim().toInt(),tripStartTimeMin)
                }
            }
        }

        private fun setArrivalTimeData(arrivalTime:Int,tripStartTime:Int) {
            val arrivalTimeMins = tripStartTime + (arrivalTime)
            val arrivalTimePair = Pair(arrivalTimeMins / 60, arrivalTimeMins % 60)
            "${arrivalTimePair.first}:${arrivalTimePair.second}".also {
                item.textViewEndTime.text = it
            }
        }
    }
}