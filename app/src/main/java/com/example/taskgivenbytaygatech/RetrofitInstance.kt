package com.example.taskgivenbytaygatech

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    private lateinit var retrofit: Retrofit

    fun getService(): CountriesAPI{

        if (retrofit == null){
            retrofit = Retrofit.Builder().baseUrl("https://89.147.202.166:1153/tayqa/tiger/api/development/test/").
                    addConverterFactory(GsonConverterFactory.create()).build()
        }
        return retrofit.create(CountriesAPI::class.java)
    }
}