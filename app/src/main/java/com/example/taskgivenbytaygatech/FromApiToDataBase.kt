package com.example.taskgivenbytaygatech

import com.example.taskgivenbytaygatech.Room.CityEntity
import com.example.taskgivenbytaygatech.Room.CountryEntity
import com.example.taskgivenbytaygatech.Room.DataBase
import com.example.taskgivenbytaygatech.Room.PeopleEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FromApiToDataBase(private val countriesApi: CountriesAPI, private val dataBase: DataBase) {

    suspend fun getDataInsertToDatabase() {
        CoroutineScope(Dispatchers.IO).launch{
            val countries = countriesApi.getCountries()

            countries?.forEach { country ->
                dataBase.countryDao().insertCountry(CountryEntity(country.countryId, country.name))

                country.cityList.forEach { city ->
                    dataBase.cityDao()
                        .insertCity(CityEntity(city.cityId, city.name, country.countryId))

                    city.peopleList.forEach { person ->
                        dataBase.peopleDao().insertPerson(PeopleEntity(person.humanId, person.name, person.surname, city.cityId))
                    }
                }
            }
        }
    }
}