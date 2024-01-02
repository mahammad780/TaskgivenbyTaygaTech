package com.example.taskgivenbytaygatech.Network

import com.example.taskgivenbytaygatech.Model.Root
import retrofit2.http.GET

interface CountriesAPI {
        @GET("TayqaTech/getdata/")
        suspend fun getCountries(): Root
}