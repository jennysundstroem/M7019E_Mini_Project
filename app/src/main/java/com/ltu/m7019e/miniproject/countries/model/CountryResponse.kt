package com.ltu.m7019e.miniproject.countries.model

import kotlinx.serialization.Serializable

@Serializable
data class CountryResponse(

    var countries: List<Country>
)
