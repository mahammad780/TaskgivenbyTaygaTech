package com.example.taskgivenbytaygatech.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "people", foreignKeys = [ForeignKey(entity = CityEntity::class, parentColumns = ["id"],
    childColumns = ["city_id"], onDelete = ForeignKey.CASCADE)])
data class PeopleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "person_name")
    val name: String,
    @ColumnInfo(name = "person_surname")
    val surName: String,
    @ColumnInfo(name = "city_id")
    val cityId: Int
)
