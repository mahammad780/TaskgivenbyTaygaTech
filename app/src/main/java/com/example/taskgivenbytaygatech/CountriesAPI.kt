package com.example.taskgivenbytaygatech

import com.example.taskgivenbytaygatech.Data.Root
import retrofit2.http.GET

interface CountriesAPI {
        @GET("TayqaTech/getdata/")
        suspend fun getCountries(): Root
}