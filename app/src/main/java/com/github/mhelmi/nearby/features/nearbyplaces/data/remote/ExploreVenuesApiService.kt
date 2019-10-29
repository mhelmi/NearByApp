package com.github.mhelmi.nearby.features.nearbyplaces.data.remote

import com.github.mhelmi.nearby.BuildConfig
import com.github.mhelmi.nearby.features.nearbyplaces.data.requests.GetVenuePhotosResponse
import com.github.mhelmi.nearby.features.nearbyplaces.data.requests.GetVenuesResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ExploreVenuesApiService {

    @GET("venues/explore")
    fun exploreVenues(
        @Query("ll") latLang: String,
        @Query("radius") radius: Int = 1000,
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int = 1,
        @Query("client_id") clientId: String = BuildConfig.FOURSQUARE_CLIENT_ID,
        @Query("client_secret") clientSecret: String = BuildConfig.FOURSQUARE_CLIENT_SECRET,
        @Query("v") v: String = "20191028"
    ): Observable<GetVenuesResponse>

    @GET("venues/{venue_id}/photos")
    fun getVenuePhotos(
        @Path("venue_id") venueId: String,
        @Query("limit") limit: Int = 1,
        @Query("offset") offset: Int = 1,
        @Query("client_id") clientId: String = BuildConfig.FOURSQUARE_CLIENT_ID,
        @Query("client_secret") clientSecret: String = BuildConfig.FOURSQUARE_CLIENT_SECRET,
        @Query("v") v: String = "20191028"
    ): Single<GetVenuePhotosResponse>
}