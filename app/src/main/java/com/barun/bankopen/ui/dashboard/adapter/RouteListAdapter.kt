package com.barun.bankopen.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.barun.bankopen.data.model.RouteInfo
import com.barun.bankopen.data.model.RouteTiming
import com.barun.bankopen.databinding.RouteListItemBinding

class RouteListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = mutableMapOf<RouteInfo, List<RouteTiming>?>()
    private var routeList = mutableListOf<RouteInfo>()
    fun addItems(map:HashMap<RouteInfo, List<RouteTiming>?>){
        items = map
        routeList = items.keys.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding = RouteListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ItemViewHolder){
            with(holder) { bind(Pair(routeList[position],items[routeList[position]])) }
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
//            item.textViewStartTime.text = routeTiming?.
        }
    }
}