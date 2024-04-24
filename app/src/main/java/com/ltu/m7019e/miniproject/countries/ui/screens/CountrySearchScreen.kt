package com.ltu.m7019e.miniproject.countries.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ltu.m7019e.miniproject.countries.model.Country
import com.ltu.m7019e.miniproject.countries.viewmodel.CountryListUiState

@Composable
fun CountrySearchScreen(
    countryListUiState: CountryListUiState,
    modifier: Modifier = Modifier
) { /*
    LazyColumn(modifier = modifier) {
        items(countryList) { country ->
            CountryListItemCard(
                country = country,
                modifier = Modifier.padding(8.dp),
                countryListItemClicked = countryListItemClicked
            )
        }
    } */

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Search screen")
    }

}
