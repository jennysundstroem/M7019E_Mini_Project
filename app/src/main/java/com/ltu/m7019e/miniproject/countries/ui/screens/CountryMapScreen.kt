package com.ltu.m7019e.miniproject.countries.ui.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.ltu.m7019e.miniproject.countries.viewmodel.SelectedCountryUiState



@Composable
fun CountryMapScreen(
    selectedCountryUiState: SelectedCountryUiState,
) {
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            when(selectedCountryUiState) {
                is SelectedCountryUiState.Success -> {
                    LazyColumn {
                        item{
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Text(
                                    text = selectedCountryUiState.country.names.common,
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                selectedCountryUiState.country.maps.googleMapsUrl?.let {
                                    WebPageButton(
                                        urlPath = it,
                                        placeHolder = "Open in Google Maps"
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                selectedCountryUiState.country.maps.openStreetMapsUrl?.let {
                                    WebPageButton(
                                        urlPath = it,
                                        placeHolder = "Open in Open Street Maps"
                                    )
                                }
                            }
                        }
                        item {
                            selectedCountryUiState.country.maps.openStreetMapsUrl?.let { url ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(600.dp)
                                        .padding(8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    WebViewSample(url = url)
                                }
                            }
                        }
                    }
                }

                is SelectedCountryUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is SelectedCountryUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Error: Something went wrong!",
                            style = MaterialTheme.typography.headlineLarge,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun WebPageButton(urlPath: String, placeHolder: String, modifier: Modifier = Modifier) {
    if (urlPath.isNotEmpty()) {
        val context = LocalContext.current

        Button(
            onClick = {
                val uri = Uri.parse(urlPath)
                val intent = Intent(Intent.ACTION_VIEW, uri)

                context.startActivity(intent)
            },

            ) {
            Text(text = placeHolder)
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
                settings.setSupportZoom(true)
                settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }
        },
        update = { webView ->
            webView.loadUrl(url)
        }
    )
}
