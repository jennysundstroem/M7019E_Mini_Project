package com.ltu.m7019e.miniproject.countries.viewmodel

import ConnectionManager
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ltu.m7019e.miniproject.countries.database.CountriesRepository
import com.ltu.m7019e.miniproject.countries.model.Country
import com.ltu.m7019e.miniproject.countries.CountriesApplication
import com.ltu.m7019e.miniproject.countries.database.SavedCountriesRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface SelectedCountryUiState {
    data class Success(
        val country: Country,
        val isFavorite : Boolean
    )
        : SelectedCountryUiState

    object Error : SelectedCountryUiState
    object Loading : SelectedCountryUiState
}

sealed interface CountryListUiState {
    data class Success(
        val countries: List<Country>) : CountryListUiState
    object Error : CountryListUiState
    object Loading : CountryListUiState

    object NoNetwork : CountryListUiState

}

class CountriesViewModel(
    private val countriesRepository: CountriesRepository,
    private val savedCountriesRepository : SavedCountriesRepository,
    private val connectionManager: ConnectionManager
) : ViewModel() {

    var countryListUiState: CountryListUiState by mutableStateOf(CountryListUiState.Loading)
        private set

    var selectedCountryUiState: SelectedCountryUiState by mutableStateOf(SelectedCountryUiState.Loading)
        private set

    private var lastCached : String = ""


    init {
        scheduleApiWorker("getAllCountries")
        getAllCountries()
    }

    fun getAllCountries() {
        viewModelScope.launch {
            if(lastCached == "allCountries") {
                Log.w("myApp", "no network, but cached")
                countryListUiState = CountryListUiState.Success(savedCountriesRepository.getCachedCountries())
            }
            else if (connectionManager.isNetworkAvailable) {
                Log.w("myApp", "has network, getting countries");
                countryListUiState = CountryListUiState.Loading
                scheduleApiWorker("getAllCountries")
                lastCached = "allCountries"
                countryListUiState = try {
                    CountryListUiState.Success(countriesRepository.getAllCountries())
                } catch (e: IOException) {
                    CountryListUiState.Error
                } catch (e: HttpException) {
                    CountryListUiState.Error
                }
            } else {
                Log.w("myApp", "no network, nothing cached")
                countryListUiState = CountryListUiState.NoNetwork
                delay(2000)
                getAllCountries()
            }

        }
    }

    fun setSelectedCountry(country: Country) {
        viewModelScope.launch {
            selectedCountryUiState = SelectedCountryUiState.Loading
            selectedCountryUiState = try {
                SelectedCountryUiState.Success(country, savedCountriesRepository.getCountry(country.names) != null)
            } catch (e: IOException) {
                SelectedCountryUiState.Error
            } catch (e: HttpException) {
                SelectedCountryUiState.Error
            }
        }
    }

    fun getSavedCountries() {
        viewModelScope.launch {
            countryListUiState = CountryListUiState.Loading
            countryListUiState = try {
                CountryListUiState.Success(savedCountriesRepository.getFavouriteCountries())
            } catch (e: IOException) {
                CountryListUiState.Error
            } catch (e: HttpException) {
                CountryListUiState.Error
            }
        }
    }

    fun saveCountry(country: Country) {
        viewModelScope.launch {
            savedCountriesRepository.insertCountry(country)
            savedCountriesRepository.setFavouriteCountry(country.names)
            selectedCountryUiState = SelectedCountryUiState.Success(country, true)
        }
    }

    fun deleteCountry(country: Country) {
        viewModelScope.launch {
            savedCountriesRepository.deleteFavoriteCountry(country.names)
            selectedCountryUiState = SelectedCountryUiState.Success(country, false)
        }
    }

    private fun scheduleApiWorker(action: String) {
        savedCountriesRepository.scheduleApiWorker(action)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CountriesApplication)
                val countriesRepository = application.container.countriesRepository
                val savedCountriesRepository = application.container.savedCountriesRepository
                val connectionManager = ConnectionManager(application.applicationContext)

                CountriesViewModel(
                    countriesRepository = countriesRepository,
                    savedCountriesRepository = savedCountriesRepository,
                    connectionManager = connectionManager
                )
            }
        }
    }
}