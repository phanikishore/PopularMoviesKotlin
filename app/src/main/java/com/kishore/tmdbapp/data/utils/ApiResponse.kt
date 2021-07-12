package com.kishore.tmdbapp.data.utils

import retrofit2.Response

sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val exception: String?) : ApiResponse<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}

suspend fun <T> safeApiCall(
    call: () -> Response<T>?,
    errorMessage: String = "Problem Fetching data at the moment!"
): ApiResponse<T> {
    try {
        val result = call()
        result?.let {response->
            if (response.isSuccessful) {
                response.body()?.let {
                    return ApiResponse.Success(it)
                }
            }
            response.errorBody()?.let {
                return try {
                    val errorString = it.string()
                    ApiResponse.Error(errorString)
                } catch (e: Exception) {
                    ApiResponse.Error(e.message ?: errorMessage)
                }
            }
        }?: return ApiResponse.Error(errorMessage)
    } catch (e: Exception) {
        return ApiResponse.Error(e.message ?: errorMessage)
    }
}