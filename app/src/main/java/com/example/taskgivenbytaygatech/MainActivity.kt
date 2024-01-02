package com.example.taskgivenbytaygatech

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.taskgivenbytaygatech.ViewModel.ViewModelApi
import com.example.taskgivenbytaygatech.ViewModel.ViewModelApiFactory
import com.example.taskgivenbytaygatech.ViewModel.ViewModelDatabaseFactory
import com.example.taskgivenbytaygatech.ViewModel.ViewModelDatabase
import com.example.taskgivenbytaygatech.Adapter.RecyclerViewAdapter
import com.example.taskgivenbytaygatech.Network.CountriesAPI
import com.example.taskgivenbytaygatech.Repository.FromApiToDataBase
import com.example.taskgivenbytaygatech.Repository.FromDataBase
import com.example.taskgivenbytaygatech.Room.CityEntity
import com.example.taskgivenbytaygatech.Room.CountryEntity
import com.example.taskgivenbytaygatech.Room.DataBase
import com.example.taskgivenbytaygatech.Room.PeopleEntity
import com.example.taskgivenbytaygatech.databinding.ActivityMainBinding
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dataBase: DataBase
    private lateinit var adapter: RecyclerViewAdapter
    private lateinit var viewModel: ViewModelApi
    private lateinit var viewModelDataBase: ViewModelDatabase
    private lateinit var adapterCitiesSpinner: ArrayAdapter<String>
    private lateinit var adapterCountriesSpinner: ArrayAdapter<String>
    private lateinit var internetSettingsLauncher: ActivityResultLauncher<Intent>
    private val MY_FILE = "MyFile"
    private val FIRST_RUN_FLAG = "firstRun"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        if (!isInternetAvailable() && isFirstRun()) {
            updateFirstRunFlag()
            showInternetDialogForFirstTime()
        }

        internetSettingsLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (isInternetAvailable()) {
                recreate()
            } else {
                showInternetDialogForFirstTime()
            }
        }

        dataBase = Room.databaseBuilder(applicationContext, DataBase::class.java, "database")
            .fallbackToDestructiveMigration().build()

        if (isInternetAvailable()) {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://89.147.202.166:1153/tayqa/tiger/api/development/test/")
                .addConverterFactory(GsonConverterFactory.create()).build()
            val countriesAPI = retrofit.create(CountriesAPI::class.java)

            val fromApiToDataBase = FromApiToDataBase(countriesAPI, dataBase)
            viewModel = ViewModelProvider(
                this, ViewModelApiFactory(fromApiToDataBase)
            )[ViewModelApi::class.java]
            viewModel.fetchData()
        }

        val fromDataBase = FromDataBase(dataBase)
        viewModelDataBase = ViewModelProvider(
            this, ViewModelDatabaseFactory(fromDataBase)
        )[ViewModelDatabase::class.java]

        setRecyclerViewAdapter()

        viewModelDataBase.getPeopleFromDB().observe(this, Observer {
            val listPeople = it.toMutableList()
            adapter.setPersons(listPeople)
        })
        viewModelDataBase.getCountriesFromDB().observe(this, Observer {
            adapterCountriesSpinner = settingCountrySpinnersAdapter(it)
            binding.spinnerCountries.adapter = adapterCountriesSpinner
        })

        binding.spinnerCountries.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    viewModelDataBase.getCitiesFromDb().observe(this@MainActivity, Observer {
                        adapterCitiesSpinner = updateCitySpinner(position, it)
                        binding.spinnerCities.adapter = adapterCitiesSpinner
                        if (binding.spinnerCountries.selectedItemPosition == 0) {
                            binding.spinnerCities.isEnabled = false
                            binding.spinnerCities.isActivated = false
                        } else {
                            binding.spinnerCities.isEnabled = true
                            binding.spinnerCities.isActivated = true
                        }
                    })
                }
            }
        binding.spinnerCities.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                viewModelDataBase.getPeopleFromDB().observe(this@MainActivity, Observer {
                    val filteredList = filteredPeopleList(
                        it, binding.spinnerCities.selectedItem.toString()
                    )
                    if (binding.spinnerCities.selectedItem == "Cities") {
                        val listPeople = it.toMutableList()
                        adapter.setPersons(listPeople)
                    } else {
                        adapter.filterByCities(filteredList)
                    }
                })
            }
        }
        binding.swipeRefresh.setColorSchemeResources(R.color.black)
        binding.swipeRefresh.setOnRefreshListener {
            viewModelDataBase.getPeopleFromDB().observe(this, Observer {
                val listPeople = it.toMutableList()
                adapter.setPersons(listPeople)
                binding.spinnerCountries.setSelection(0)
                binding.swipeRefresh.isRefreshing = false
            })
        }
    }


    private fun setRecyclerViewAdapter() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = RecyclerViewAdapter()
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter
    }

    private fun settingCountrySpinnersAdapter(countries: List<CountryEntity>): ArrayAdapter<String> {
        val country: MutableList<String> = mutableListOf()
        country.addAll(countries.map { it.name })
        country.add(0, "Countries")
        val adapterCountriesSpinner = ArrayAdapter(this, R.layout.mu_spinner_item, country)
        adapterCountriesSpinner.setDropDownViewResource(R.layout.mu_dropdown_item)
        adapterCountriesSpinner.notifyDataSetChanged()
        return adapterCountriesSpinner
    }

    private fun updateCitySpinner(countryId: Int, cities: List<CityEntity>): ArrayAdapter<String> {
        val filteredCities = cities.filter { city ->
            city.countryId == countryId
        }
        val cityName = mutableListOf<String>()

        cityName.addAll(filteredCities.map { filteredCity ->
            filteredCity.name
        })
        cityName.add(0, "Cities")
        val adapterCitiesSpinner = ArrayAdapter(this, R.layout.mu_spinner_item, cityName)
        adapterCitiesSpinner.setDropDownViewResource(R.layout.mu_dropdown_item)
        adapterCitiesSpinner.notifyDataSetChanged()
        return adapterCitiesSpinner
    }

    private fun filteredPeopleList(
        people: List<PeopleEntity>, currentCityName: String
    ): MutableList<PeopleEntity> {
        val filteredPeopleList = mutableListOf<PeopleEntity>()
        for (person in people) {
            if (person.cityName.equals(currentCityName)) {
                filteredPeopleList.add(person)
            }
        }
        return filteredPeopleList
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)

        return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

    private fun showInternetDialogForFirstTime() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Internet Access Required")
        builder.setMessage("Please enable internet access to use this app.")
        builder.setPositiveButton("Enable") { _, _ ->
            val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
            internetSettingsLauncher.launch(settingsIntent)
        }
        builder.setNegativeButton("Cancel") { _, _ ->
            finish()
        }
        builder.setCancelable(false)
        builder.show()
    }

    private fun isFirstRun(): Boolean {
        val settings = getSharedPreferences(MY_FILE, Context.MODE_PRIVATE)
        return settings.getBoolean(FIRST_RUN_FLAG, true)
    }

    private fun updateFirstRunFlag() {
        val settings = getSharedPreferences(MY_FILE, Context.MODE_PRIVATE)
        val editor = settings.edit()
        editor.putBoolean(FIRST_RUN_FLAG, false)
        editor.apply()
    }
}
