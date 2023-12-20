package com.example.taskgivenbytaygatech.Data

data class Countries(
    val countryId: Int,
    val name: String,
    val cityList: List<Cities>
)
