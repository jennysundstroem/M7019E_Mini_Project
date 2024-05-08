package com.ltu.m7019e.miniproject.countries.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ltu.m7019e.miniproject.countries.model.Country
import com.ltu.m7019e.miniproject.countries.model.CountryName

@Dao
interface CountryDao {
    @Query("SELECT * FROM countries WHERE isFavourite = 1")
    suspend fun getFavoriteCountries(): List<Country>

    @Query("UPDATE countries SET isFavourite = 1 WHERE names = :name")
    suspend fun setFavouriteCountry(name: CountryName)

    @Query("UPDATE countries SET isFavourite = 0 WHERE names = :name")
    suspend fun deleteFavoriteCountry(name: CountryName)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCountry(country: Country)

    @Query("UPDATE countries SET isCached = 1 WHERE names = :name")
    suspend fun setCachedCountry(name: CountryName)

    @Query("SELECT * FROM countries WHERE names = :name")
    suspend fun getCountry(name : CountryName): Country

    @Query("DELETE FROM countries WHERE isCached = 1 AND isFavourite = 0")
    suspend fun deleteCachedCountries()
    @Query("SELECT * FROM countries WHERE isCached = 1")
    suspend fun getCachedCountries(): List<Country>

}