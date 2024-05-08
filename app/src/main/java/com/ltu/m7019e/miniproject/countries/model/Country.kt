package com.ltu.m7019e.miniproject.countries.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "countries")
data class Country(
    @PrimaryKey
    @SerialName(value = "name")
    var names: CountryName,

    @SerialName(value = "capital")
    val capital: List<String>? = null,

    @SerialName(value = "region")
    val region: String,

    @SerialName(value = "subregion")
    val subregion: String? = null,

    @SerialName(value = "population")
    val population: Int,

    @SerialName(value = "area")
    val area: Double,

    @SerialName(value = "languages")
    val languages: Map<String, String>? = null,

    @SerialName(value = "currencies")
    val currencies: Map<String, CountryCurrency>? = null,

    @SerialName(value = "flags")
    val flagUrl: CountryFlag,

    var isFavourite: Boolean = false,

    var isCached: Boolean = false

    //TODO: Fix these
    //@SerialName(value = "googleMapsUrl")
    //val googleMapsUrl: String,

    //@SerialName(value = "openStreetMapsUrl")
    //val openStreetMapsUrl: String
)

