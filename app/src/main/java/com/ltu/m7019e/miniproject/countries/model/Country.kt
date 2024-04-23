
package com.ltu.m7019e.miniproject.countries.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Country(
    @SerialName(value = "name")
    val name: CountryName,
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
    val languages: List<CountryLanguage> = listOf(),
    @SerialName(value = "currencies")
    val currencies: List<CountryCurrency> = listOf(),
    @SerialName(value = "flags")
    val flags: CountryFlag,
    //val googleMapsUrl: String,
    //val openStreetMapsUrl: String
)
