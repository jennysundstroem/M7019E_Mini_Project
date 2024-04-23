package com.ltu.m7019e.miniproject.countries.model
//TODO
data class Country(
    val name: String,
    val officialName: String,
    val nativeName: Map<String, String>,
    val capital: List<String>,
    val region: String,
    val subregion: String,
    val population: Int,
    val area: Double,
    val languages: Map<String, String>,
    val currencies: Map<String, String>,
    val flagUrl: String,
    val googleMapsUrl: String,
    val openStreetMapsUrl: String
)
