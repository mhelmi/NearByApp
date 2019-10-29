package com.github.mhelmi.nearby.general.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.mhelmi.nearby.features.nearbyplaces.data.Place
import com.github.mhelmi.nearby.features.nearbyplaces.data.local.VenuesDao
import com.github.mhelmi.nearby.features.nearbyplaces.data.requests.Venue

@Database(entities = [Place::class], version = 2, exportSchema = false)
abstract class NearbyRoomDatabase : RoomDatabase() {

    abstract fun venueDao(): VenuesDao
}