package com.kishore.tmdbapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TmdbApplication:Application() {
    private val mTAG = TmdbApplication::class.java.simpleName
}