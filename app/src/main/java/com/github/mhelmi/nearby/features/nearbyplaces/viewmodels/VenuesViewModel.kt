package com.github.mhelmi.nearby.features.nearbyplaces.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mhelmi.nearby.features.nearbyplaces.data.VenuesRepository
import com.github.mhelmi.nearby.features.nearbyplaces.data.requests.Venue
import com.github.mhelmi.nearby.general.ResultType
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class VenuesViewModel constructor(private val venuesRepository: VenuesRepository) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val _venueList = MutableLiveData<List<Venue>>()
    private val _shouldShowLoading = MutableLiveData<Boolean>()
    private val _shouldShowEmptyDataError = MutableLiveData<Boolean>()
    private val _shouldShowGeneralError = MutableLiveData<Boolean>()
    val venueList: LiveData<List<Venue>> = _venueList
    val shouldShowLoading: LiveData<Boolean> = _shouldShowLoading
    val shouldShowEmptyDataError: LiveData<Boolean> = _shouldShowEmptyDataError
    val shouldShowGeneralError: LiveData<Boolean> = _shouldShowGeneralError

    fun getAllVenues(location: String) {
        disposable.add(
            venuesRepository.getExploreVenues(location)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _shouldShowLoading.postValue(true) }
                .subscribe({
                    _shouldShowLoading.postValue(false)
                    _shouldShowEmptyDataError.postValue(false)
                    _shouldShowGeneralError.postValue(false)
                    when (it.resultType) {
                        ResultType.SUCCESS -> _venueList.postValue(it.data)
                        ResultType.EMPTY_DATA -> _shouldShowEmptyDataError.postValue(true)
                        else -> _shouldShowGeneralError.postValue(true)
                    }
                }, {
                    _shouldShowLoading.postValue(false)
                    _shouldShowEmptyDataError.postValue(false)
                    _shouldShowGeneralError.postValue(true)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        if (!disposable.isDisposed) disposable.dispose()
    }
}