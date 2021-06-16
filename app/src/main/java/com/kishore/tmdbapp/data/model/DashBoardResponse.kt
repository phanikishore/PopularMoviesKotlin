package com.kishore.tmdbapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class DashBoardResponse(
    var popularMovies: MoviesResponse? = null,
    var topMovies: MoviesResponse? = null,
)