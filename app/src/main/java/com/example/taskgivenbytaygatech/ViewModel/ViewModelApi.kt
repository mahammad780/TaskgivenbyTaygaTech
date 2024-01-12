package com.example.taskgivenbytaygatech.ViewModel


import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskgivenbytaygatech.MainActivity
import com.example.taskgivenbytaygatech.Network.NoInternetException
import com.example.taskgivenbytaygatech.Repository.FromApiToDataBase
import com.example.taskgivenbytaygatech.Room.CityEntity
import com.example.taskgivenbytaygatech.Room.CountryEntity
import com.example.taskgivenbytaygatech.Room.PeopleEntity
import kotlinx.coroutines.launch

class ViewModelApi(
    private val fromApiToDataBase: FromApiToDataBase,
    private val context: Context,
    private val activity: AppCompatActivity
) : ViewModel() {

    private val MY_FILE = "MyFile"
    private val FIRST_RUN_FLAG = "firstRun"

    private val internetSettingsLauncher = activity.registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (isInternetAvailable()) {
            val intent = Intent(this.activity, MainActivity::class.java)
            activity.startActivity(intent)
            activity.finish()
        } else {
            showInternetDialogForFirstTime()
        }
    }

    fun fetchData() {
        viewModelScope.launch {
            try {
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
            } catch (e: NoInternetException) {
                if (isFirstRun()) {
                    updateFirstRunFlag()
                    showInternetDialogForFirstTime()
                } else {
                    Toast.makeText(
                        context,
                        "Make sure your device is connected to the Internet",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }
    }

    private fun showInternetDialogForFirstTime() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Internet Access Required")
        builder.setMessage("Please enable internet access to use this app.")
        builder.setPositiveButton("Enable") { _, _ ->
            val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
            internetSettingsLauncher.launch(settingsIntent)
        }
        builder.setNegativeButton("Cancel") { _, _ ->
            activity.finish()
        }
        builder.setCancelable(false)
        builder.show()
    }

    private fun isFirstRun(): Boolean {
        val settings = activity.getSharedPreferences(MY_FILE, Context.MODE_PRIVATE)
        return settings.getBoolean(FIRST_RUN_FLAG, true)
    }

    private fun updateFirstRunFlag() {
        val settings = activity.getSharedPreferences(MY_FILE, Context.MODE_PRIVATE)
        val editor = settings.edit()
        editor.putBoolean(FIRST_RUN_FLAG, false)
        editor.apply()
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)

        return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }
}
