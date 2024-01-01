package com.example.taskgivenbytaygatech.Adapter

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.taskgivenbytaygatech.Repository.FromDataBase
import com.example.taskgivenbytaygatech.Room.CityEntity
import com.example.taskgivenbytaygatech.Room.CountryEntity
import com.example.taskgivenbytaygatech.Room.PeopleEntity

class AppViewModelForDatabase(private val fromDataBase: FromDataBase) : ViewModel() {
    fun getPeopleFromDB(): LiveData<List<PeopleEntity>> {
        return fromDataBase.getAllPeople()
    }

    fun getCountriesFromDB(): LiveData<List<CountryEntity>> {
        return fromDataBase.getAllCountries()
    }

    fun getCitiesFromDb(): LiveData<List<CityEntity>> {
        return fromDataBase.getAllCities()
    }
}