package com.example.taskgivenbytaygatech.Repository

import androidx.lifecycle.LiveData
import com.example.taskgivenbytaygatech.Room.CityEntity
import com.example.taskgivenbytaygatech.Room.CountryEntity
import com.example.taskgivenbytaygatech.Room.DataBase
import com.example.taskgivenbytaygatech.Room.PeopleEntity

class FromDataBase(private val dataBase: DataBase) {
    fun getAllPeople(): LiveData<List<PeopleEntity>> {
        return dataBase.peopleDao().getAllPersons()
    }

    fun getAllCountries(): LiveData<List<CountryEntity>> {
        return dataBase.countryDao().getAllCountries()
    }

    fun getAllCities(): LiveData<List<CityEntity>> {
        return dataBase.cityDao().getAllCities()
    }
}