package com.example.taskgivenbytaygatech.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities")
data class CityEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val countryId: Int
)
