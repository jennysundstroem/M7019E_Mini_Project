package com.ltu.m7019e.miniproject.countries.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ltu.m7019e.miniproject.countries.model.Country

@Dao
interface CountryDao {
    @Query("SELECT * FROM favorite_countries")
    suspend fun getFavoriteCountries(): List<Country>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteCountry(country: Country)

    @Query("SELECT * FROM favorite_countries WHERE names = :name")
    suspend fun getCountry(name: String): Country

    @Query("DELETE FROM favorite_countries WHERE names = :name")
    suspend fun deleteFavoriteCountry(name: String)
}