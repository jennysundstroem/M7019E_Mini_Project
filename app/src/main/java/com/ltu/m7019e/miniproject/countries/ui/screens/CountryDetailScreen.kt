package com.ltu.m7019e.miniproject.countries.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ltu.m7019e.miniproject.countries.model.Country

@Composable
fun CountryDetailScreen(country: Country,
                        modifier: Modifier = Modifier
) {
    Column {
        Box {
            AsyncImage(
                model = country.flagUrl,
                contentDescription = country.name,
                modifier = modifier
                    .width(110.dp)
                    .height(80.dp),
                //contentScale = ContentScale.Crop
            )
        }
        Text(text = country.name, style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.size(8.dp))
        Text(text = country.region, style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = country.subregion,
            style = MaterialTheme.typography.bodySmall,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.size(8.dp))

    }
}