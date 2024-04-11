package com.ltu.m7019e.miniproject.countries.database

import com.ltu.m7019e.miniproject.countries.model.Country

class Countries {
    fun getCountries(): List<Country> {
        return listOf<Country>(
            Country(
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
            Country(
                name = "Eritrea",
            officialName = "State of Eritrea",
            nativeName = mapOf("ara" to "دولة إرتريا", "eng" to "State of Eritrea", "ti" to "ሃገረ ኤርትራ"),
            capital = listOf("Asmara"),
            region = "Africa",
            subregion = "Eastern Africa",
            population = 3546421,
            area = 117600.0,
            languages = mapOf("ara" to "Arabic", "eng" to "English", "ti" to "Tigrinya"),
            currencies = mapOf("ERN" to "Eritrean nakfa"),
            flagUrl = "https://flagcdn.com/w320/er.png",
            googleMapsUrl = "https://goo.gl/maps/Uc3zSnZcj1D3i71t8",
            openStreetMapsUrl = "https://www.openstreetmap.org/relation/192759"
            ),
            Country(
            name = "Liberia",
            officialName = "Republic of Liberia",
            nativeName = mapOf("eng" to "Republic of Liberia"),
            capital = listOf("Monrovia"),
            region = "Africa",
            subregion = "Western Africa",
            population = 4818977,
            area = 111369.0,
            languages = mapOf("eng" to "English"),
            currencies = mapOf("LRD" to "Liberian dollar"),
            flagUrl = "https://flagcdn.com/w320/lr.png",
            googleMapsUrl = "https://goo.gl/maps/Gv8PKtcgrLJkDxNa9",
            openStreetMapsUrl = "https://www.openstreetmap.org/relation/192830"
        ),
            Country(
            name = "Bermuda",
            officialName = "Bermuda",
            nativeName = mapOf("eng" to "Bermuda"),
            capital = listOf("Hamilton"),
            region = "Americas",
            subregion = "Northern America",
            population = 62070,
            area = 54.0,
            languages = mapOf("eng" to "English"),
            currencies = mapOf("BMD" to "Bermudian dollar"),
            flagUrl = "https://flagcdn.com/w320/bm.png",
            googleMapsUrl = "https://goo.gl/maps/MZJGavDfg8Vtd3Ha7",
            openStreetMapsUrl = "https://www.openstreetmap.org/relation/4203162"
        ),
            Country(
            name = "Vatican City",
            officialName = "Vatican City",
            nativeName = mapOf("ita" to "Città del Vaticano"),
            capital = listOf("Vatican City"),
            region = "Europe",
            subregion = "Southern Europe",
            population = 825,
            area = 0.44,
            languages = mapOf("ita" to "Italian", "lat" to "Latin"),
            currencies = mapOf("EUR" to "Euro"),
            flagUrl = "https://flagcdn.com/w320/va.png",
            googleMapsUrl = "https://goo.gl/maps/YjzV1bYYt3j4moiW9",
            openStreetMapsUrl = "https://www.openstreetmap.org/relation/1695895"
        )
        )
    }
}