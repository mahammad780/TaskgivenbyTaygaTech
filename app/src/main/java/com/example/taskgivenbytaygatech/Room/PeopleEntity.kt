package com.example.taskgivenbytaygatech.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "people")
data class PeopleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val surName: String,
    @ColumnInfo("city_name")
    val cityName: String
)
