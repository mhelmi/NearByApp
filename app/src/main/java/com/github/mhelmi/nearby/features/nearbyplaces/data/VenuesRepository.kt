package com.github.mhelmi.nearby.features.nearbyplaces.data

import com.github.mhelmi.nearby.utils.extentions.loge
import com.github.mhelmi.nearby.features.nearbyplaces.data.local.VenuesDao
import com.github.mhelmi.nearby.features.nearbyplaces.data.remote.ExploreVenuesApiService
import com.github.mhelmi.nearby.features.nearbyplaces.data.requests.Item
import com.github.mhelmi.nearby.features.nearbyplaces.data.requests.Venue
import com.github.mhelmi.nearby.general.Result
import com.github.mhelmi.nearby.utils.SUCCESS
import io.reactivex.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class VenuesRepository @Inject constructor(
    val exploreVenuesApiService: ExploreVenuesApiService,
    val venuesDao: VenuesDao
) {

    fun getExploreVenues(location: String, radius: Int = 1000): Observable<Result<List<Venue>>> {
        return exploreVenuesApiService.exploreVenues(location, radius)
            .switchMap {
                return@switchMap if (it.meta.code == SUCCESS) {
                    val items: List<Item> = it.response.groups.flatMap { group ->
                        return@flatMap group.items
                    }
                    val venues = items.map { item -> item.venue }
                    Observable.fromIterable(venues)
                        .flatMapSingle { venue -> getVenuePhotos(venue) }
                        .toList()
                        .toObservable()
                } else Observable.error(Throwable())
            }.map { venues ->
                if (venues.isEmpty())
                    Result.emptyDataError()
                else
                    Result.success(venues)
            }
            .doOnError { loge("getExploreVenues: onError:: ${it.message}") }
    }

    private fun getVenuePhotos(venue: Venue): Single<Venue> {
        return exploreVenuesApiService.getVenuePhotos(venue.id)
            .delay(2, TimeUnit.SECONDS)
            .map {
                return@map if (it.meta.code == SUCCESS) {
                    val photos = it.response.photos.photoList
                    if (photos.isNotEmpty()) {
                        val photo = photos[0]
                        /* original: the original photo’s size */
                        val size = "original"
                        venue.photoUrl = photo.prefix + size + photo.suffix
                    }
                    venue
                } else venue
            }
            .onErrorReturnItem(venue)
            .doOnError { loge("getVenuePhotos: onError:: ${it.message}") }
    }
}