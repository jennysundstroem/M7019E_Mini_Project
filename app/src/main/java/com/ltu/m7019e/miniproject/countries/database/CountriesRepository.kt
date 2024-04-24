package com.ltu.m7019e.miniproject.countries.database

import com.ltu.m7019e.miniproject.countries.model.CountryResponse
import com.ltu.m7019e.miniproject.countries.network.CountriesApiService

interface CountriesRepository {
    suspend fun getAllCountries(): CountryResponse
    suspend fun getSearchedCountries(name: String): CountryResponse
}


class NetworkCountriesRepository(private val apiService: CountriesApiService) : CountriesRepository {
    override suspend fun getAllCountries(): CountryResponse {
        return apiService.getAllCountries()
    }

    override suspend fun getSearchedCountries(name: String): CountryResponse {
        return apiService.getSearchedCountries(name)
    }

}