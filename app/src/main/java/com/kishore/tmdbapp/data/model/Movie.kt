package com.kishore.tmdbapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Movie(
    var adult: Boolean? = null,
    @SerialName("backdrop_path")
    var backdropPath: String? = null,
    @SerialName("genre_ids")
    var genreIds: List<Int?>? = null,
    var id: Int? = null,
    @SerialName("original_language")
    var originalLanguage: String? = null,
    @SerialName("original_title")
    var originalTitle: String? = null,
    var overview: String? = null,
    var popularity: Double? = null,
    @SerialName("poster_path")
    var posterPath: String? = null,
    @SerialName("release_date")
    var releaseDate: String? = null,
    var title: String? = null,
    var video: Boolean? = null,
    @SerialName("vote_average")
    var voteAverage: Double? = null,
    @SerialName("vote_count")
    var voteCount: Int? = null

) : Parcelable {

    override fun toString(): String {
        return "Movie(adult=$adult, backdropPath=$backdropPath, genreIds=$genreIds, id=$id, originalLanguage=$originalLanguage, originalTitle=$originalTitle, overview=$overview, popularity=$popularity, posterPath=$posterPath, releaseDate=$releaseDate, title=$title, video=$video, voteAverage=$voteAverage, voteCount=$voteCount)"
    }

}
