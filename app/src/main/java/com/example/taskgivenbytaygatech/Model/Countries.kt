package com.example.taskgivenbytaygatech.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Countries(
    @SerializedName("countryId")
    @Expose
    val countryId: Int,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("cityList")
    @Expose
    val cityList: List<Cities>
)
