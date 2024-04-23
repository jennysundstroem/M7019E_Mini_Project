package com.ltu.m7019e.miniproject.countries.viewmodel

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
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface SelectedCountryUiState {
    data class Success(
        val country: Country,
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
}

class CountriesViewModel(private val countriesRepository: CountriesRepository) : ViewModel() {

    var countryListUiState: CountryListUiState by mutableStateOf(CountryListUiState.Loading)
        private set

    var selectedCountryUiState: SelectedCountryUiState by mutableStateOf(SelectedCountryUiState.Loading)
        private set

    init {
        getAllCountries()
    }

    private fun getAllCountries() {
        viewModelScope.launch {
            countryListUiState = CountryListUiState.Loading
            countryListUiState = try {
                CountryListUiState.Success(countriesRepository.getAllCountries().countries)
            } catch (e: IOException) {
                CountryListUiState.Error
            } catch (e: HttpException) {
                CountryListUiState.Error
            }
        }
    }

    fun setSelectedCountry(country: Country) {
        viewModelScope.launch {
            selectedCountryUiState = SelectedCountryUiState.Loading
            selectedCountryUiState = try {
                SelectedCountryUiState.Success(country)
            } catch (e: IOException) {
                SelectedCountryUiState.Error
            } catch (e: HttpException) {
                SelectedCountryUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CountriesApplication)
                val countriesRepository = application.container.countriesRepository
                CountriesViewModel(countriesRepository = countriesRepository)
            }
        }
    }
}