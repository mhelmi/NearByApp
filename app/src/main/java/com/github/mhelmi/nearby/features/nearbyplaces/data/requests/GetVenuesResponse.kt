package com.github.mhelmi.nearby.features.nearbyplaces.data.requests

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Keep
data class GetVenuesResponse(
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("response")
    val response: VenuesResponse
)

@Keep
data class VenuesResponse(
    @SerializedName("groups") val groups: List<Group>,
    @SerializedName("headerFullLocation") val headerFullLocation: String,
    @SerializedName("headerLocation") val headerLocation: String,
    @SerializedName("headerLocationGranularity") val headerLocationGranularity: String,
    @SerializedName("query") val query: String,
    @SerializedName("totalResults") val totalResults: Int
)

@Keep
data class Group(
    @SerializedName("items") val items: List<Item>
)

@Keep
data class Item(
    @SerializedName("venue") val venue: Venue
)

@Keep
//@Entity(tableName = "venues_table")
data class Venue(
    @SerializedName("id") val id: String,
    @SerializedName("categories") val categories: List<Category>,
    @SerializedName("location") val location: Location,
    @SerializedName("name") val name: String,
    @SerializedName("rating") val rating: Double,
    @SerializedName("url") val url: String,
    var photoUrl: String
)

@Keep
@Entity(tableName = "venue_categories_table")
data class Category(
    @PrimaryKey @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("pluralName") val pluralName: String,
    @SerializedName("primary") val primary: Boolean,
    @SerializedName("shortName") val shortName: String
)

@Keep
@Entity(tableName = "venue_locations_table")
data class Location(
//    @PrimaryKey val id: String,
    @SerializedName("address") val address: String,
    @SerializedName("cc") val cc: String,
    @SerializedName("city") val city: String,
    @SerializedName("country") val country: String,
    @SerializedName("crossStreet") val crossStreet: String,
    @SerializedName("distance") val distance: Int,
    @SerializedName("formattedAddress") val formattedAddress: List<String>,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double,
    @SerializedName("postalCode") val postalCode: String,
    @SerializedName("state") val state: String
)