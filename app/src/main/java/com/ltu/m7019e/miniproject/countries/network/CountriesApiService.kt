package com.ltu.m7019e.miniproject.countries.network


import com.ltu.m7019e.miniproject.countries.model.CountryResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CountriesApiService {

    @GET("all")
    suspend fun getAllCountries(
    ): CountryResponse

    @GET("name/{name}")
    suspend fun getSearchedCountries(
        @Path("name") name: String,
    ): CountryResponse

}