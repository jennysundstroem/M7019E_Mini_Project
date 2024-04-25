package com.ltu.m7019e.miniproject.countries.database

import com.ltu.m7019e.miniproject.countries.model.Country
import com.ltu.m7019e.miniproject.countries.model.CountryResponse
import com.ltu.m7019e.miniproject.countries.network.CountriesApiService

interface CountriesRepository {
    suspend fun getAllCountries(): List<Country>
    suspend fun getSearchedCountries(name: String): CountryResponse
}


class NetworkCountriesRepository(private val apiService: CountriesApiService) : CountriesRepository {
    override suspend fun getAllCountries(): List<Country> {
        return apiService.getAllCountries()
    }

    override suspend fun getSearchedCountries(name: String): CountryResponse {
        return apiService.getSearchedCountries(name)
    }

}