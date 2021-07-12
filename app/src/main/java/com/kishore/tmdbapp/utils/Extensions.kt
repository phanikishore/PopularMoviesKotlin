package com.kishore.tmdbapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide

fun String.prefixUrl(url:String) = String.format(url,this)

fun AppCompatImageView.loadUrl(url:String){
    Glide.with(this.context).load(url).into(this)
}

fun Int.ofPercent(percentage: Int): Double {
    return (this * percentage).div(100.0)
}

fun Context.isNetworkAvailable(): Boolean {
    (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).also { cm ->
        cm.activeNetwork?.let { network ->
            val bool = cm.getNetworkCapabilities(network)
                ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            return bool!!
        }
    }
    return false
}