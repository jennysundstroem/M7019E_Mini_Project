package com.ltu.m7019e.miniproject.countries.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountryName(
    @SerialName(value = "common")
    var common: String,

    @SerialName(value = "official")
    var official: String,

)
