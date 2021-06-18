package com.barun.bankopen.data.persistance

import javax.inject.Inject

class RouteDBRepo @Inject constructor(val routeInfoDao: RouteInfoDao,val routeTimingDao: RouteTimingDao){
    private val mTAG = RouteDBRepo::class.java.simpleName
}