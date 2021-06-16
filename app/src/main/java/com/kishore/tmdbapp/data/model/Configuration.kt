package com.kishore.tmdbapp.data.model
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


@Serializable
data class Configuration(
    @SerialName("change_keys")
    var changeKeys: List<String?>? = null,
    var images: Images? = null
) {
    @Serializable
    data class Images(
        @SerialName("backdrop_sizes")
        var backdropSizes: List<String?>? = null,
        @SerialName("base_url")
        var baseUrl: String? = null,
        @SerialName("logo_sizes")
        var logoSizes: List<String?>? = null,
        @SerialName("poster_sizes")
        var posterSizes: List<String?>? = null,
        @SerialName("profile_sizes")
        var profileSizes: List<String?>? = null,
        @SerialName("secure_base_url")
        var secureBaseUrl: String? = null,
        @SerialName("still_sizes")
        var stillSizes: List<String?>? = null
    )
}
