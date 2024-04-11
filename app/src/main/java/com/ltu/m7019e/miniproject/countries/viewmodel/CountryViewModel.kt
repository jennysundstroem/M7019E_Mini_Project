package com.ltu.m7019e.miniproject.countries.viewmodel

import androidx.lifecycle.ViewModel
import com.ltu.m7019e.miniproject.countries.database.CountriesUIState
import com.ltu.m7019e.miniproject.countries.model.Country
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CountryViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(CountriesUIState())
    val uiState: StateFlow<CountriesUIState> = _uiState.asStateFlow()

    fun setSelectedCountry(country: Country) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedCountry = country
            )
        }
    }
}