package com.example.taskgivenbytaygatech.Data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class People(
    @SerializedName("humanId")
    @Expose
    val humanId: Int,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("surname")
    @Expose
    val surname: String
)
