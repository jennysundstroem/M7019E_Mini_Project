package com.ltu.m7019e.miniproject.countries.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountryCurrency (
    @SerialName(value = "name")
    var name: String,

    @SerialName(value = "symbol")
    var symbol: String,
)