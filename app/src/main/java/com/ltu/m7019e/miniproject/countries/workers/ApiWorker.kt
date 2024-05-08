package com.ltu.m7019e.miniproject.countries.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ltu.m7019e.miniproject.countries.database.DefaultAppContainer

class ApiWorker(
    ctx: Context,
    params: WorkerParameters
) : CoroutineWorker(ctx, params) {
    private val container = DefaultAppContainer(ctx)
    override suspend fun doWork(): Result {
        return try {
            val action = inputData.getString("action")
            when (action) {
                "getAllCountries" -> {
                    val countries = container.countriesRepository.getAllCountries()
                    container.savedCountriesRepository.deleteCachedCountries()
                    //for all movies in the list, save them to the database
                    countries.forEach { country ->
                        container.savedCountriesRepository.insertCountry(country)
                        container.savedCountriesRepository.setCachedCountry(country.names)
                    }
                }
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}