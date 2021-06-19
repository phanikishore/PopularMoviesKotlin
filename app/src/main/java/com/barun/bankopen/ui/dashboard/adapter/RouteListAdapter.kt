package com.barun.bankopen.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.barun.bankopen.data.model.RouteInfo
import com.barun.bankopen.data.model.RouteResponse
import com.barun.bankopen.data.model.RouteTiming
import com.barun.bankopen.databinding.RouteListItemBinding

class RouteListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = mapOf<String, List<RouteTiming>?>()
    private var routeList = listOf<RouteInfo>()
    fun addItems(routeResponse: RouteResponse){
        routeResponse.routeTimings?.let {
            items = it
        }
        routeResponse.routeInfo?.let {
            routeList = it
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding = RouteListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ItemViewHolder){
            with(holder) { bind(Pair(routeList[position],items[routeList[position].id])) }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ItemViewHolder(val item: RouteListItemBinding):RecyclerView.ViewHolder(item.root){
        fun bind(data: Pair<RouteInfo,List<RouteTiming>?>){
            val routeInfo = data.first
            val routeTiming = data.second
            item.textViewOrigin.text = routeInfo.source
            item.textViewDestination.text = routeInfo.destination
            if(routeTiming?.isEmpty() != true){
                item.textViewStartTime.text = routeTiming?.get(0)?.tripStartTime
            }
        }
    }
}