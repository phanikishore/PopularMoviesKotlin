package com.kishore.tmdbapp.data.model

import com.kishore.tmdbapp.data.base.BaseResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponse(
    var page: Int? = null,
    @SerialName("total_results") var totalResults: Int? = null,
    @SerialName("total_pages") var totalPages: Int? = null,
    @SerialName("results") var movies: MutableList<Movie>? = null
) : BaseResponse()
