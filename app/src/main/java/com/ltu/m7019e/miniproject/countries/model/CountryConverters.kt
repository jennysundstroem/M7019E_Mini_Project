package com.ltu.m7019e.miniproject.countries.model

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter

@ProvidedTypeConverter
class CountryConverters {
    @TypeConverter
    fun fromCountryName(countryName: CountryName): String {
        return "${countryName.common},${countryName.official}"
    }

    @TypeConverter
    fun toCountryName(countryName: String): CountryName {
        val parts = countryName.split(",")
        return CountryName(parts[0], parts[1])
    }

    @TypeConverter
    fun fromCountryCurrency(countryCurrency: CountryCurrency): String {
        return "${countryCurrency.name},${countryCurrency.symbol}"
    }

    @TypeConverter
    fun toCountryCurrency(countryCurrency: String): CountryCurrency {
        val parts = countryCurrency.split(",")
        return CountryCurrency(parts[0], parts[1])
    }

    @TypeConverter
    fun fromCountryFlag(countryFlag: CountryFlag): String {
        return countryFlag.flagpng
    }

    @TypeConverter
    fun toCountryFlag(countryFlag: String): CountryFlag {
        return CountryFlag(countryFlag)
    }

    @TypeConverter
    fun fromCapital(capital: List<String>?): String {
        return capital?.joinToString(",") ?: ""
    }

    @TypeConverter
    fun toCapital(capital: String): List<String> {
        return capital.split(",")
    }
    @TypeConverter
    fun fromLanguages(languages: Map<String, String>?): String {
        return languages?.map { "${it.key},${it.value}" }?.joinToString(";") ?: ""
    }

    @TypeConverter
    fun toLanguages(languages: String): Map<String, String> {
        return languages.split(";").map {
            val parts = it.split(",")
            parts[0] to parts[1]
        }.toMap()
    }

    @TypeConverter
    fun fromCurrencies(currencies: Map<String, CountryCurrency>?): String {
        return currencies?.map { "${it.key},${it.value}" }?.joinToString(";") ?: ""
    }
    @TypeConverter
    fun toCurrencies(currencies: String): Map<String, CountryCurrency> {
        return currencies.split(";").map {
            val parts = it.split(",")
            parts[0] to CountryCurrency(parts[0], parts[1])
        }.toMap()
    }
}