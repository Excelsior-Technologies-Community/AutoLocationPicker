package com.ext.locationpicker.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "states")
data class StateEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val countryId: Int
)
