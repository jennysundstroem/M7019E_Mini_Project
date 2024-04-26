package com.ltu.m7019e.miniproject.countries.database

import com.ltu.m7019e.miniproject.countries.model.Country
import com.ltu.m7019e.miniproject.countries.model.CountryName
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

interface SavedCountriesRepository {
    suspend fun getSavedCountries(): List<Country>
    suspend fun insertCountry(country: Country)
    suspend fun getCountry(name: CountryName): Country
    suspend fun deleteCountry(country: Country)
}

class FavoriteCountriesRepository(private val countriesDao: CountryDao) : SavedCountriesRepository {
    override suspend fun getSavedCountries(): List<Country> {
        return countriesDao.getFavoriteCountries()
    }

    override suspend fun insertCountry(country: Country) {
        countriesDao.insertFavoriteCountry(country)
    }

    override suspend fun getCountry(name: CountryName): Country {
        return countriesDao.getCountry(name)
    }

    override suspend fun deleteCountry(country: Country) {
        countriesDao.deleteFavoriteCountry(country)
    }

}