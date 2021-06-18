package com.barun.bankopen.data.repository

import com.barun.bankopen.data.model.RouteResponse
import retrofit2.http.GET


interface OpenBankApi {

    @GET("/data")
    suspend fun getLatestRoutes(): RouteResponse?
}