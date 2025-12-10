package com.ext.locationpicker.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class CountryEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val iso2: String
)
