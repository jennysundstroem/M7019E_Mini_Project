package com.ltu.m7019e.miniproject.countries.database

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.ltu.m7019e.miniproject.countries.network.CountriesApiService
import com.ltu.m7019e.miniproject.countries.utils.Constants
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

interface AppContainer {
    val countriesRepository: CountriesRepository
    val savedCountriesRepository: SavedCountriesRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {

    fun getLoggerInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    val countriesJson = Json {
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
        .addConverterFactory(countriesJson.asConverterFactory("application/json".toMediaType()))
        .baseUrl(Constants.COUNTRIES_BASE_URL)
        .build()

    private val retrofitService: CountriesApiService by lazy {
        retrofit.create(CountriesApiService::class.java)
    }

    override val countriesRepository: CountriesRepository by lazy {
        NetworkCountriesRepository(retrofitService)
    }
    override val savedCountriesRepository: SavedCountriesRepository by lazy {
        FavoriteCountriesRepository(CountryDatabase.getDatabase(context).countryDao(), context)
    }
}