package com.ltu.m7019e.miniproject.countries.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountryMap (
    @SerialName(value = "googleMaps")
    val googleMapsUrl: String? = null,

    @SerialName(value = "openStreetMaps")
    val openStreetMapsUrl: String? = null
)