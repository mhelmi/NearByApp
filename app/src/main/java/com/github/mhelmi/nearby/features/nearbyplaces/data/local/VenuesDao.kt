package com.github.mhelmi.nearby.features.nearbyplaces.data.local

import androidx.room.Dao
import androidx.room.Query
import com.github.mhelmi.nearby.features.nearbyplaces.data.requests.Venue
import com.github.mhelmi.nearby.general.BaseDao
import io.reactivex.Maybe

@Dao
abstract class VenuesDao /*: BaseDao<Venue>*/ {
//    @Query("SELECT * FROM venues_table")
//    abstract fun getVenues(): Maybe<List<Venue>>
}