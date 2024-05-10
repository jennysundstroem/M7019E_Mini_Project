package com.ltu.m7019e.miniproject.countries.database

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.ltu.m7019e.miniproject.countries.model.Country
import com.ltu.m7019e.miniproject.countries.model.CountryName
import com.ltu.m7019e.miniproject.countries.model.CountryResponse
import com.ltu.m7019e.miniproject.countries.network.CountriesApiService
import com.ltu.m7019e.miniproject.countries.workers.ApiWorker

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
    suspend fun getFavouriteCountries(): List<Country>

    suspend fun setFavouriteCountry(name: CountryName)

    suspend fun deleteFavoriteCountry(name: CountryName)

    suspend fun insertCountry(country: Country)

    suspend fun setCachedCountry(name: CountryName)

    suspend fun getCountry(name: CountryName): Country

    suspend fun deleteCachedCountries()

    suspend fun getCachedCountries(): List<Country>

    abstract fun scheduleApiWorker(action: String)


}

class FavoriteCountriesRepository(private val countriesDao: CountryDao, context: Context) : SavedCountriesRepository {
    private val workManager = WorkManager.getInstance(context)
    override suspend fun getFavouriteCountries(): List<Country> {
        return countriesDao.getFavoriteCountries()
    }

    override suspend fun setFavouriteCountry(name: CountryName) {
        return countriesDao.setFavouriteCountry(name)
    }

    override suspend fun deleteFavoriteCountry(name: CountryName) {
        return countriesDao.deleteFavoriteCountry(name)
    }

    override suspend fun insertCountry(country: Country) {
        countriesDao.insertCountry(country)
    }

    override suspend fun setCachedCountry(name: CountryName) {
        return countriesDao.setCachedCountry(name)
    }

    override suspend fun getCountry(name: CountryName): Country {
        return countriesDao.getCountry(name)
    }

    override suspend fun deleteCachedCountries() {
        return countriesDao.deleteCachedCountries()
    }

    override suspend fun getCachedCountries(): List<Country> {
        return countriesDao.getCachedCountries()
    }

    override fun scheduleApiWorker(action: String) {
        val inputData = workDataOf("action" to action)
        val workRequest = OneTimeWorkRequestBuilder<ApiWorker>()
            .setInputData(inputData)
            .build()
        workManager.enqueue(workRequest)
    }


}