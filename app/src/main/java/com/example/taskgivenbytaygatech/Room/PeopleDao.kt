package com.example.taskgivenbytaygatech.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PeopleDao {
    @Insert
    suspend fun insertPerson(person: PeopleEntity)

    @Query("SELECT * FROM people WHERE cityId = :cityId")
    suspend fun getPersonByCity(cityId: Int): List<PeopleEntity>

    @Query("SELECT * FROM people")
    suspend fun getAllPersons(): List<PeopleEntity>
}