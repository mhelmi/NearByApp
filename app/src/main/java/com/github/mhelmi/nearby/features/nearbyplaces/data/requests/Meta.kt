package com.github.mhelmi.nearby.features.nearbyplaces.data.requests

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Meta(
    @SerializedName("code")
    val code: Int,
    @SerializedName("requestId")
    val requestId: String
)