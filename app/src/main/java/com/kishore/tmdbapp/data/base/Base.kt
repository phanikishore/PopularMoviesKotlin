package com.kishore.tmdbapp.data.base

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
abstract class BaseResponse{
    @SerialName("status_code")
    var code:Int? = null
    @SerialName("status_message")
    var statusMessage:String?=null
}