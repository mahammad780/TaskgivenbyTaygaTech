package com.example.taskgivenbytaygatech.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: List<CityEntity>)

    @Query("SELECT * FROM cities")
     fun getAllCities(): LiveData<List<CityEntity>>

}