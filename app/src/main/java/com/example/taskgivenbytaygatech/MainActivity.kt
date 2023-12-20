package com.example.taskgivenbytaygatech

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.taskgivenbytaygatech.Adapter.RecyclerViewAdapter
import com.example.taskgivenbytaygatech.Room.CityEntity
import com.example.taskgivenbytaygatech.Room.CountryEntity
import com.example.taskgivenbytaygatech.Room.DataBase
import com.example.taskgivenbytaygatech.Room.PeopleEntity
import com.example.taskgivenbytaygatech.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var fromApiToDataBase: FromApiToDataBase
    private lateinit var dataBase: DataBase
    private lateinit var adapter: RecyclerViewAdapter
    private lateinit var peopleList: MutableList<PeopleEntity>
    private lateinit var countriesArray: ArrayList<CountryEntity>
    private lateinit var citiesArray: ArrayList<CityEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

       initDatabaseAndRetrofit()
        initRecyclerView()

        binding.swipeRefresh.setColorSchemeResources(R.color.black)
        binding.swipeRefresh.setOnRefreshListener{
            binding.spinner2.isEnabled = false
            binding.spinner.clearChildFocus(binding.spinner2)
            binding.spinner.clearFocus()
            initDatabaseAndRetrofit()

        }



        val adapterCountriesSpinner = ArrayAdapter(this,android.R.layout.simple_spinner_item,countriesArray.map { it.name })
        adapterCountriesSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapterCountriesSpinner

        val adapterCitiesSpinner = ArrayAdapter(this,android.R.layout.simple_spinner_item, mutableListOf<String>())
        adapterCitiesSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner2.adapter = adapterCitiesSpinner

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val selectedCountry = countriesArray[p2]
                updateCitySpinner(selectedCountry.id)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                binding.spinner2.isEnabled = false
            }

        }

        binding.spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val selectedCity = citiesArray[p2].id
                val filteredList = filteredPeopleList(peopleList,selectedCity)
                adapter.filterByCities(filteredList)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }


   private fun initDatabaseAndRetrofit() {
        dataBase= Room.databaseBuilder(applicationContext, DataBase::class.java, "database").build()
        val countriesApi = Retrofit.Builder().baseUrl("https:http://89.147.202.166:1153/tayqa/tiger/api/development/test/")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(CountriesAPI::class.java)
        lifecycleScope.launch {
        fromApiToDataBase = FromApiToDataBase(countriesApi, dataBase)
        fromApiToDataBase.getDataInsertToDatabase()
       }
       binding.swipeRefresh.isRefreshing = false
    }


    private fun initRecyclerView(){
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        lifecycleScope.launch {
                peopleList = dataBase.peopleDao().getAllPersons().toMutableList()
                countriesArray = dataBase.countryDao().getAllCountries() as ArrayList<CountryEntity>
                citiesArray = dataBase.cityDao().getAllCities() as ArrayList<CityEntity>
        }
                adapter = RecyclerViewAdapter(peopleList)
                binding.recyclerView.adapter = adapter

    }



    fun updateCitySpinner(countryId: Int){
        val filteredCities = citiesArray.filter { it.countryId == countryId }
        val cityName = filteredCities.map {it.name}

        val citiesAdapter = binding.spinner2.adapter as ArrayAdapter<String>
        citiesAdapter.clear()
        citiesAdapter.addAll(cityName)
        citiesAdapter.notifyDataSetChanged()
    }

    private fun filteredPeopleList(people: MutableList<PeopleEntity>, currentCityId: Int): MutableList<PeopleEntity>{
        val filteredPeopleList = mutableListOf<PeopleEntity>()
        for(person in people){
            if(person.cityId == currentCityId){
                filteredPeopleList.add(person)
            }
        }
        return filteredPeopleList
    }

}