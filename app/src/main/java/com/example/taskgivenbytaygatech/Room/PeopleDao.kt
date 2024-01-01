package com.example.taskgivenbytaygatech.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PeopleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerson(person: List<PeopleEntity>)

    @Query("SELECT * FROM people")
     fun getAllPersons(): LiveData<List<PeopleEntity>>
}