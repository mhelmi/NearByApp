package com.github.mhelmi.nearby.features.nearbyplaces.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "places_table")
data class Place(
    @PrimaryKey val id: String,
    val title: String
)