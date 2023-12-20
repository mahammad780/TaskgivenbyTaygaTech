package com.example.taskgivenbytaygatech.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CityDao {
    @Insert
    suspend fun insertCity(city: CityEntity)

    @Query("SELECT * FROM cities WHERE countryId = :countryId")
    suspend fun getCityByCountry(countryId: Int): List<CityEntity>

    @Query("SELECT * FROM cities")
    suspend fun getAllCities(): List<CityEntity>

}