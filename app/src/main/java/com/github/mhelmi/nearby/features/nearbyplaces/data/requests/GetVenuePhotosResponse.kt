package com.github.mhelmi.nearby.features.nearbyplaces.data.requests

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GetVenuePhotosResponse(
    @SerializedName("meta") val meta: Meta,
    @SerializedName("response") val response: PhotosResponse
)

@Keep
data class PhotosResponse(
    @SerializedName("photos")
    val photos: Photos
)

@Keep
data class Photos(
    @SerializedName("count")
    val count: Int,
    @SerializedName("items")
    val photoList: List<Photo>
)

@Keep
data class Photo(
    @SerializedName("id")
    val id: String,
    @SerializedName("createdAt")
    val createdAt: Int,
    @SerializedName("prefix")
    val prefix: String,
    @SerializedName("suffix")
    val suffix: String,
    @SerializedName("width")
    val width: Int,
    @SerializedName("height")
    val height: Int
)