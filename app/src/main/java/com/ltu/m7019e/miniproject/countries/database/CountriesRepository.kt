package com.ltu.m7019e.miniproject.countries.database

import com.ltu.m7019e.miniproject.countries.model.CountryResponse
import com.ltu.m7019e.miniproject.countries.network.CountriesAPIService

interface CountriesRepository {
    suspend fun getAllCountries(): CountryResponse
    suspend fun getSearchedCountries(): CountryResponse
}

class NetworkCountriesRepository{

}