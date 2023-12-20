package com.example.taskgivenbytaygatech

import com.example.taskgivenbytaygatech.Data.Countries
import retrofit2.Call
import retrofit2.http.GET

interface CountriesAPI {
    @GET("TayqaTech/getdata")
    suspend fun getCountries(): List<Countries>
}