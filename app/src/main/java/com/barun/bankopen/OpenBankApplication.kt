package com.barun.bankopen

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
@HiltAndroidApp
class OpenBankApplication : Application(), Configuration.Provider {
    private val mTAG = OpenBankApplication::class.java.simpleName

    @Inject
    lateinit var workerFactory: HiltWorkerFactory
    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    private val pendingIntent by lazy {
        PendingIntent.getBroadcast(
            applicationContext,
            1,
            Intent(applicationContext, TriggerWorkerTaskReceiver::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private val alarmManager by lazy {
        getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    override fun onCreate() {
        super.onCreate()
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  5000L,(60 * 1000).toLong(), pendingIntent)
    }

    override fun onTerminate() {
        super.onTerminate()
        alarmManager.cancel(pendingIntent)
    }
}