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
    private val _loading = MutableLiveData<Boolean>()
    private val _emptyDataError = MutableLiveData<Boolean>()
    private val _error = MutableLiveData<Boolean>()
    val venueList: LiveData<List<Venue>> = _venueList
    val loading: LiveData<Boolean> = _loading
    val emptyDataError: LiveData<Boolean> = _emptyDataError
    val error: LiveData<Boolean> = _error

    fun getAllVenues(location: String) {
        disposable.add(
            venuesRepository.getExploreVenues(location)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _loading.postValue(true) }
                .subscribe({
                    _loading.postValue(false)
                    when (it.resultType) {
                        ResultType.SUCCESS -> {
                            _venueList.postValue(it.data)
                            _emptyDataError.postValue(false)
                            _error.postValue(false)
                        }
                        ResultType.EMPTY_DATA -> {
                            _emptyDataError.postValue(true)
                            _error.postValue(false)
                        }
                        else -> {
                            _emptyDataError.postValue(false)
                            _error.postValue(true)
                        }
                    }
                }, {
                    _loading.postValue(false)
                    _emptyDataError.postValue(false)
                    _error.postValue(true)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        if (!disposable.isDisposed) disposable.dispose()
    }
}