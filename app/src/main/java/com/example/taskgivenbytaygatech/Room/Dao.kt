package com.example.taskgivenbytaygatech.Room

import androidx.core.app.Person
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

@Dao
interface CityDao {
    @Insert
    suspend fun insertCity(city: CityEntity)

    @Query("SELECT * FROM cities WHERE countryId = :countryId")
    suspend fun getCityByCountry(countryId: Int): List<CityEntity>

    @Query("SELECT * FROM cities")
    suspend fun getAllCities(): List<CityEntity>

}

@Dao
interface PeopleDao {
    @Insert
    suspend fun insertPerson(person: PeopleEntity)

    @Query("SELECT * FROM people WHERE cityId = :cityId")
    suspend fun getPersonByCity(cityId: Int): List<PeopleEntity>

    @Query("SELECT * FROM people")
    suspend fun getAllPersons(): List<PeopleEntity>
}