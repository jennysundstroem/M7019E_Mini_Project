package com.ltu.m7019e.miniproject.countries.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.ui.Alignment
import androidx.compose.ui.viewinterop.AndroidView
import com.ltu.m7019e.miniproject.countries.model.Country
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Switch
import com.ltu.m7019e.miniproject.countries.viewmodel.CountriesViewModel
import com.ltu.m7019e.miniproject.countries.viewmodel.CountryListUiState
import com.ltu.m7019e.miniproject.countries.viewmodel.SelectedCountryUiState

@Composable
fun CountryDetailScreen(
    selectedCountryUiState: SelectedCountryUiState,
    modifier: Modifier = Modifier,
    countriesViewModel: CountriesViewModel
) {
    val selectedCountryUiState = countriesViewModel.selectedCountryUiState
    when (selectedCountryUiState) {
        is SelectedCountryUiState.Success -> {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = selectedCountryUiState.country.names.common,
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                )

                Text(
                    text = selectedCountryUiState.country.names.common,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                )
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = selectedCountryUiState.country.flagUrl.flagpng,
                        contentDescription = selectedCountryUiState.country.names.common,
                        modifier = modifier
                            .width(300.dp)
                            .height(200.dp),
                    )
                }
                Spacer(modifier = Modifier.size(8.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(), // Ensure the Column takes full width
                    horizontalAlignment = Alignment.Start // Align the content to the start (left)
                ) {
                    Text(
                        //TODO
                        text = "Capital: " //+ selectedCountryUiState.country.capital.toString(),
                        , style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(3.dp)
                    )
                    Text(
                        text = "Region: " + selectedCountryUiState.country.region,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(3.dp)
                    )
                    Text(
                        text = "Sub-Region: " + selectedCountryUiState.country.subregion,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(3.dp)
                    )
                    Text(
                        text = "Capital: " + selectedCountryUiState.country.capital.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(3.dp)
                    )

                }
                Spacer(modifier = Modifier.size(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    //TODO: WebViewSample(url = country.openStreetMapsUrl)
                }
                Row {
                    Text(
                        text = "Favorite",
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Switch(checked = selectedCountryUiState.isFavorite, onCheckedChange = {
                        if (it)
                        countriesViewModel.saveCountry(selectedCountryUiState.country)
                        else countriesViewModel.deleteCountry(selectedCountryUiState.country)
                    })
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
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewSample(
    url: String,
    webViewClient: WebViewClient = WebViewClient()
) {

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                this.webViewClient = webViewClient
                settings.javaScriptEnabled = true
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.setSupportZoom(true)            }
        },
        update = { webView ->
            webView.loadUrl(url)
        }
    )
}