package com.example.taskgivenbytaygatech.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Cities(
    @SerializedName("cityId")
    @Expose
    val cityId: Int,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("peopleList")
    @Expose
    val peopleList: List<People>
)
