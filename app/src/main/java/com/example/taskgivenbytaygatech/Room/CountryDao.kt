package com.example.taskgivenbytaygatech.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CountryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(country: List<CountryEntity>)

    @Query("SELECT * FROM countries")
     fun getAllCountries(): LiveData<List<CountryEntity>>

    @Query("SELECT COUNT(*) FROM countries")
     fun getRowCount(): Int
}