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

@Composable
fun CountryDetailScreen(
    country: Country,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = country.name,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
        )

        Text(
            text = country.officialName,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
        )
        Box(
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = country.flagUrl,
                contentDescription = country.name,
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
                text = "Capital: " + country.capital.joinToString(", "),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(3.dp)
            )
            Text(
                text = "Region: " + country.region, style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(3.dp)
            )
            Text(
                text = "Sub-Region: " + country.subregion,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(3.dp)
            )
            Text(
                text = "Capital: " + country.capital.joinToString(", "),
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
            WebViewSample(url = country.openStreetMapsUrl)
        }    }
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