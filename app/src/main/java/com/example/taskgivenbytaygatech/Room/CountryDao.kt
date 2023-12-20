package com.example.taskgivenbytaygatech.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CountryDao {
    @Insert
    suspend fun insertCountry(country: CountryEntity)

    @Query("SELECT * FROM countries")
    suspend fun getAllCountries(): List<CountryEntity>
}