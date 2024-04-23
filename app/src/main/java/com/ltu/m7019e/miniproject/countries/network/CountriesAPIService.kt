package com.ltu.m7019e.miniproject.countries.network

import com.ltu.m7019e.miniproject.countries.model.CountryResponse
import retrofit2.http.GET

interface CountriesAPIService {
    @GET("all")
    suspend fun getAllCountries(
    ): CountryResponse
}