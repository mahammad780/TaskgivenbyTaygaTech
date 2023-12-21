package com.example.taskgivenbytaygatech.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "cities", foreignKeys = [ForeignKey(entity = CountryEntity::class, parentColumns = ["id"],
    childColumns = ["country_id"], onDelete = ForeignKey.CASCADE)])
data class CityEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "cities_name")
    val name: String,
    @ColumnInfo(name = "country_id")
    val countryId: Int
)
