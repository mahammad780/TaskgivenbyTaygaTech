package com.example.taskgivenbytaygatech.Repository

import com.example.taskgivenbytaygatech.Network.CountriesAPI
import com.example.taskgivenbytaygatech.Model.Root
import com.example.taskgivenbytaygatech.Room.CityEntity
import com.example.taskgivenbytaygatech.Room.CountryEntity
import com.example.taskgivenbytaygatech.Room.DataBase
import com.example.taskgivenbytaygatech.Room.PeopleEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FromApiToDataBase(private var countriesAPI: CountriesAPI, private val dataBase: DataBase) {

    suspend fun getDataFromApi(): Root {
        return withContext(Dispatchers.IO) {
            countriesAPI.getCountries()
        }
    }

    suspend fun putToDataBase(
        countries: List<CountryEntity>, cities: List<CityEntity>, people: List<PeopleEntity>
    ) {
        dataBase.countryDao().insertCountry(countries)
        dataBase.cityDao().insertCity(cities)
        dataBase.peopleDao().insertPerson(people)
    }

}


