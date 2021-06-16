package com.kishore.tmdbapp.utils

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.appcompat.widget.AppCompatImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

fun AppCompatImageView.loadUrl(url: String) {
    Picasso.with(this.context).load(url).into(this, Callback.EmptyCallback())
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