package com.example.taskgivenbytaygatech.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class CountryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String
)
