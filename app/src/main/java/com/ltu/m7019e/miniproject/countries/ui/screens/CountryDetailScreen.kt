package com.ltu.m7019e.miniproject.countries.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.material3.Card
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import com.ltu.m7019e.miniproject.countries.model.Country
import com.ltu.m7019e.miniproject.countries.viewmodel.CountriesViewModel
import com.ltu.m7019e.miniproject.countries.viewmodel.SelectedCountryUiState

@Composable
fun CountryDetailScreen(
    selectedCountryUiState: SelectedCountryUiState,
    modifier: Modifier,
    countriesViewModel: CountriesViewModel,
    onMapClicked: (Country) -> Unit,
) {
    val selectedCountryUiState = countriesViewModel.selectedCountryUiState
    when (selectedCountryUiState) {
        is SelectedCountryUiState.Success -> {
            Card(
                modifier = modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
            ) {

                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {

                    Box(
                        modifier = Modifier
                            .size(178.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = selectedCountryUiState.country.names.common,
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.Bold

                            )
                        AsyncImage(
                            model = selectedCountryUiState.country.flagUrl.flagpng,
                            contentDescription = selectedCountryUiState.country.names.common,
                            modifier = modifier
                                .width(300.dp)
                                .height(200.dp),
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = selectedCountryUiState.country.names.common,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    val isFavorite = !selectedCountryUiState.isFavorite
                                    if (isFavorite) {
                                        countriesViewModel.saveCountry(selectedCountryUiState.country)
                                    } else {
                                        countriesViewModel.deleteCountry(selectedCountryUiState.country)
                                    }
                                }
                        ) {
                            Icon(
                                imageVector = if (selectedCountryUiState.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = if (selectedCountryUiState.isFavorite) "Favorite" else "Not favorite"
                            )
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column {
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("Region: ")
                                    }
                                    append(selectedCountryUiState.country.region)
                                },
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("Population: ")
                                    }
                                    append("${selectedCountryUiState.country.population}")
                                },
                                style = MaterialTheme.typography.bodySmall
                            )

                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("Currencies: ")
                                    }
                                    append("${    selectedCountryUiState.country.currencies?.entries?.joinToString(separator = "\n") {
                                        "${it.key}: ${it.value.name}"
                                    }}")
                                },
                                style = MaterialTheme.typography.bodySmall
                            )

                        }
                        Spacer(modifier = Modifier.size(55.dp))
                        Column {
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("Sub-Region: ")
                                    }
                                    append("${selectedCountryUiState.country.subregion}")
                                },
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("Area: ")
                                    }
                                    append("${selectedCountryUiState.country.area}")
                                },
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("Languages: ")
                                    }
                                    append("${selectedCountryUiState.country.languages?.values?.joinToString()}")
                                },
                                style = MaterialTheme.typography.bodySmall
                            )

                        }
                    }

                    Spacer(modifier = Modifier.size(8.dp))
                    Button(
                        onClick = { onMapClicked(selectedCountryUiState.country)
                        }
                    ) {
                        Text(text = "Map Information")
                    }
                }

            }

        }

        is SelectedCountryUiState.Loading -> {
            Text(
                text = "Loading...",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(16.dp)
            )
        }

        is SelectedCountryUiState.Error -> {
            Text(
                text = "Error: Something went wrong!",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(16.dp)
            )
        }

        else -> {
            Text(
                text = "Error: Something went wrong!",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(16.dp)
            )}
    }
}

