package com.example.taskgivenbytaygatech.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CountryEntity::class, CityEntity::class, PeopleEntity::class], version = 1)
abstract class DataBase : RoomDatabase() {
    abstract fun countryDao(): CountryDao
    abstract fun cityDao(): CityDao
    abstract fun peopleDao(): PeopleDao
}