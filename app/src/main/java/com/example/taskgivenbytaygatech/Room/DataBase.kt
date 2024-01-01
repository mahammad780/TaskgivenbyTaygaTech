package com.example.taskgivenbytaygatech.Room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CountryEntity::class, CityEntity::class, PeopleEntity::class], version = 7)
abstract class DataBase : RoomDatabase() {
    abstract fun countryDao(): CountryDao
    abstract fun cityDao(): CityDao
    abstract fun peopleDao(): PeopleDao
}