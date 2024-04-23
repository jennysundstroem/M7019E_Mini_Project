package com.ltu.m7019e.miniproject.countries.database

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.ltu.m7019e.miniproject.countries.network.CountriesAPIService
import com.ltu.m7019e.miniproject.countries.utils.Constants
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

interface AppContainer {
    val countriesRepository: CountriesRepository
}

class DefaultAppContainer : AppContainer {

    fun getLoggerInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    val movieDBJson = Json {
        ignoreUnknownKeys = true
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .client(
            okhttp3.OkHttpClient.Builder()
                .addInterceptor(getLoggerInterceptor())
                .connectTimeout(20, java.util.concurrent.TimeUnit.SECONDS)
                .readTimeout(20, java.util.concurrent.TimeUnit.SECONDS)
                .build()
        )
        .addConverterFactory(movieDBJson.asConverterFactory("application/json".toMediaType()))
        .baseUrl(Constants.COUNTRIES_BASE_URL)
        .build()

    private val retrofitService: CountriesAPIService by lazy {
        retrofit.create(CountriesAPIService::class.java)
    }

    override val moviesRepository: CountriesRepository by lazy {
        NetworkCountriesRepository(retrofitService)
    }
}