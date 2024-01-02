package com.example.taskgivenbytaygatech.ViewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskgivenbytaygatech.Repository.FromApiToDataBase
import com.example.taskgivenbytaygatech.Room.CityEntity
import com.example.taskgivenbytaygatech.Room.CountryEntity
import com.example.taskgivenbytaygatech.Room.PeopleEntity
import kotlinx.coroutines.launch

class ViewModelApi(private val fromApiToDataBase: FromApiToDataBase) : ViewModel() {

    fun fetchData() {
        viewModelScope.launch {
            val dataFromApi = fromApiToDataBase.getDataFromApi().countries

            fromApiToDataBase.putToDataBase(dataFromApi.map { country ->
                CountryEntity(country.countryId, country.name)
            }, dataFromApi.flatMap { country ->
                country.cityList.map { city ->
                    CityEntity(city.cityId, city.name, country.countryId)
                }
            }, dataFromApi.flatMap { country ->
                country.cityList.flatMap { city ->
                    city.peopleList.map { person ->
                        PeopleEntity(person.humanId, person.name, person.surname, city.name)
                    }
                }
            })
        }
    }
}
