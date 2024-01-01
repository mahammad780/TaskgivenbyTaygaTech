package com.example.taskgivenbytaygatech.Data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Root(
    @SerializedName("countryList")
    @Expose
    val countries: List<Countries>
)

