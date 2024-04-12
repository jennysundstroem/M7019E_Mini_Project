package com.ltu.m7019e.miniproject.countries.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ltu.m7019e.miniproject.countries.model.Country
import com.ltu.m7019e.miniproject.countries.ui.theme.CountriesTheme

@Composable
fun CountryListScreen(countryList: List<Country>, countryListItemClicked : (Country) -> Unit,
                      modifier: Modifier = Modifier){
    LazyColumn (modifier = modifier){
        items(countryList){ country ->
            CountryListItemCard(
                country = country,
                modifier = Modifier.padding(8.dp),
                countryListItemClicked = countryListItemClicked
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryListItemCard(
    country : Country,
    modifier: Modifier = Modifier,
    countryListItemClicked: (Country) -> Unit
){
    Card(modifier = modifier
        //.padding(8.dp)
        .fillMaxWidth(),
        onClick = { countryListItemClicked(country) }
        ) {
        Row {
            Box {
                AsyncImage(
                    model = country.flagUrl,
                    contentDescription = country.name,
                    modifier = modifier
                        .width(140.dp)
                        .height(80.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.size(26.dp))
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .weight(1f) // Occupy remaining space in the row
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Text(text = country.name, style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
    }
}

/*
* Text(text = movie.title, style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = movie.releaseDate, style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis)
                Spacer(modifier = Modifier.size(8.dp)) */

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