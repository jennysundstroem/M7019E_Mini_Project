package com.ltu.m7019e.miniproject.countries.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Country(
    @SerialName(value = "names")
    var names: CountryName,

    @SerialName(value = "capital")
    val capital: List<String>,

    @SerialName(value = "region")
    val region: String,

    @SerialName(value = "subregion")
    val subregion: String,

    @SerialName(value = "population")
    val population: Int,

    @SerialName(value = "area")
    val area: Double,

    @SerialName(value = "languages")
    val languages: Map<String, String>,

    @SerialName(value = "currencies")
    val currencies: CountryCurrency,

    @SerialName(value = "flags")
    val flagUrl: CountryFlag,

    //TODO: Fix these
    //@SerialName(value = "googleMapsUrl")
    //val googleMapsUrl: String,

    //@SerialName(value = "openStreetMapsUrl")
    //val openStreetMapsUrl: String
)
