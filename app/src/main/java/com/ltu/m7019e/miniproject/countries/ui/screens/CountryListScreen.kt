package com.ltu.m7019e.miniproject.countries.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ltu.m7019e.miniproject.countries.model.Country
import com.ltu.m7019e.miniproject.countries.viewmodel.CountriesViewModel
import com.ltu.m7019e.miniproject.countries.viewmodel.CountryListUiState

@Composable
fun CountryListScreen(
    countryListUiState: CountryListUiState,
    countryListItemClicked: (Country) -> Unit,
    countriesViewModel: CountriesViewModel,
    modifier: Modifier = Modifier,
    columns: Int

) {

    Column(modifier = modifier.fillMaxSize()) {
        LazyVerticalGrid(modifier = Modifier.weight(1f),
            columns = GridCells.Fixed(columns)) {
            when (countryListUiState) {
                is CountryListUiState.Success -> {
                    items((countryListUiState as CountryListUiState.Success).countries) { country ->
                        CountryListItemCard(
                            country = country,
                            modifier = Modifier.padding(8.dp),
                            countryListItemClicked = countryListItemClicked,
                            countriesViewModel = countriesViewModel
                        )
                    }

                }

                is CountryListUiState.Loading -> {
                    item {
                        Text(
                            text = "Loading...",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                is CountryListUiState.Error -> {
                    item {
                        Text(
                            text = "Error: Something went wrong!",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                CountryListUiState.NoNetwork ->
                    item {
                        Column {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "No Internet Connection",
                                modifier = Modifier.size(200.dp)
                            )
                            Text(
                                text = "No Network Connection!",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryListItemCard(
    country: Country,
    modifier: Modifier = Modifier,
    countryListItemClicked: (Country) -> Unit,
    countriesViewModel: CountriesViewModel,
) {

    var isFavorite by remember { mutableStateOf(false) }

    LaunchedEffect(country) {
        isFavorite = countriesViewModel.isFavourite(country)
    }
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { countryListItemClicked(country) },
        shape = MaterialTheme.shapes.medium,

        ){
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
            ) {
                AsyncImage(
                    model = country.flagUrl.flagpng,
                    contentDescription = country.names.common,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = country.names.common,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Capital: ${country.capital?.firstOrNull() ?: "N/A"}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Region: ${country.region}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        if (!isFavorite) {
                            countriesViewModel.saveCountry(country)
                            isFavorite = true
                        } else {
                            countriesViewModel.deleteCountry(country)
                            isFavorite = false
                        }
                    }
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = if (isFavorite) "Favorite" else "Not favorite"
                )
            }
        }

    }
}

/*
@Composable
fun Button(
onClick = { onSearchCountryClicked() }
){
    Text(text = "Reviews")
} */

/*
@Preview(showBackground = true)
@Composable
fun CountryItemPreview() {
    CountriesTheme{
        CountryListItemCard(country = Country(
            name = "Cyprus",
            officialName = "Republic of Cyprus",
            nativeName = mapOf("ell" to "Δημοκρατία της Κύπρος", "tur" to "Kıbrıs Cumhuriyeti"),
            capital = listOf("Nicosia"),
            region = "Europe",
            subregion = "Southern Europe",
            population = 1207361,
            area = 9251.0,
            languages = mapOf("ell" to "Greek", "tur" to "Turkish"),
            currencies = mapOf("EUR" to "Euro"),
            flagUrl = "https://flagcdn.com/w320/cy.png",
            googleMapsUrl = "https://goo.gl/maps/77hPBRdLid8yD5Bm7",
            openStreetMapsUrl = "https://www.openstreetmap.org/relation/307787"
        ),
        )
    }
} */