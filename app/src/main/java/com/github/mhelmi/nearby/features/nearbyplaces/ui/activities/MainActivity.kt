package com.github.mhelmi.nearby.features.nearbyplaces.ui.activities

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatToggleButton
import androidx.core.location.LocationManagerCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mhelmi.nearby.R
import com.github.mhelmi.nearby.features.nearbyplaces.data.requests.Venue
import com.github.mhelmi.nearby.features.nearbyplaces.ui.adapters.VenuesAdapter
import com.github.mhelmi.nearby.features.nearbyplaces.ui.services.TrackingService
import com.github.mhelmi.nearby.features.nearbyplaces.viewmodels.VenuesViewModel
import com.github.mhelmi.nearby.general.ViewModelFactory
import com.github.mhelmi.nearby.utils.LOCATION_EXTRA
import com.github.mhelmi.nearby.utils.LOCATION_UPDATES
import com.github.mhelmi.nearby.utils.LocationUpdateMode
import com.github.mhelmi.nearby.utils.SessionManager
import com.github.mhelmi.nearby.utils.extentions.appComponent
import com.github.mhelmi.nearby.utils.extentions.loge
import com.github.mhelmi.nearby.utils.extentions.openLocationSettings
import com.github.mhelmi.nearby.utils.extentions.setVisible
import com.google.android.gms.location.LocationServices
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.layout_empty_data.*
import kotlinx.android.synthetic.main.layout_error.*
import kotlinx.android.synthetic.main.layout_loading_progress.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var activity: AppCompatActivity
    @Inject
    lateinit var sessionManager: SessionManager
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var venueViewModel: VenuesViewModel
    private val venuesAdapter: VenuesAdapter by lazy { VenuesAdapter() }
    private val disposable: CompositeDisposable by lazy { CompositeDisposable() }
    private val trackingServiceIntent: Intent by lazy {
        Intent(activity, TrackingService::class.java)
    }
    private var isFirstTimeToLoad = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        activity = this
        appComponent.inject(this)
        setupVenueRecycler()
        initViewModels()
        setupObservers()
    }

    private fun setupVenueRecycler() {
        recyclerViewVenues.layoutManager = LinearLayoutManager(activity)
        recyclerViewVenues.adapter = venuesAdapter
    }

    private fun initViewModels() {
        venueViewModel =
            ViewModelProvider(activity, viewModelFactory).get(VenuesViewModel::class.java)
    }

    private fun setupObservers() {
        venueViewModel.venueList.observe(this, Observer<List<Venue>> { submitVenueList(it) })
        venueViewModel.shouldShowLoading.observe(this, Observer<Boolean> { shouldShowLoading(it) })
        venueViewModel.shouldShowEmptyDataError.observe(
            this, Observer<Boolean> { shouldShowEmptyDataError(it) }
        )
        venueViewModel.shouldShowGeneralError.observe(
            this,
            Observer<Boolean> { shouldShowGeneralError(it) })
    }

    fun loadVenues(location: String) {
        venueViewModel.getAllVenues(location)
    }

    private fun submitVenueList(venues: List<Venue>) {
        venuesAdapter.submitList(venues)
    }

    private fun shouldShowLoading(isLoading: Boolean) {
        if (isLoading) {
            if (isFirstTimeToLoad) layoutLoadingProgress.setVisible(isLoading)
            isFirstTimeToLoad = false
        } else {
            layoutLoadingProgress.setVisible(isLoading)
        }
    }

    private fun shouldShowEmptyDataError(emptyData: Boolean) {
        layoutEmptyData.setVisible(emptyData)
        if (emptyData) venuesAdapter.submitList(null)
    }

    private fun shouldShowGeneralError(error: Boolean) {
        layoutError.setVisible(error)
        if (error) venuesAdapter.submitList(null)
    }

    private fun getLastKnowingLocation() {
        val client = LocationServices.getFusedLocationProviderClient(this)
        client.lastLocation.addOnSuccessListener {
            loadVenues("${it.latitude},${it.longitude}")
        }
    }

    private fun startTrackingService() {
        startService(trackingServiceIntent)
    }

    private fun stopTrackingService() {
        stopService(trackingServiceIntent)
    }

    private fun checkLocationPermissions() {
        val rxPermissions = RxPermissions(activity)
        disposable.add(
            rxPermissions
                .request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe({ granted ->
                    if (granted) {
                        val locationManager =
                            getSystemService(Context.LOCATION_SERVICE) as LocationManager
                        if (LocationManagerCompat.isLocationEnabled(locationManager)) {
                            when (sessionManager.locationUpdateMode) {
                                LocationUpdateMode.RUNTIME -> startTrackingService()
                                LocationUpdateMode.SINGLE_UPDATE -> {
                                    stopTrackingService()
                                    getLastKnowingLocation()
                                }
                            }
                        } else {
                            openLocationSettings()
                        }
                    } else {
                        openLocationSettings()
                    }
                }, { loge("checkLocationPermissions: ${it.message}") })
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val toggleButton =
            menu?.findItem(R.id.actionLocationUpdateModeToggle)?.actionView as AppCompatToggleButton
        updateToggleBtn(toggleButton)
        setToggleBtnChangeListener(toggleButton)
        return super.onPrepareOptionsMenu(menu)
    }

    private fun updateToggleBtn(toggleButton: AppCompatToggleButton) {
        when (sessionManager.locationUpdateMode) {
            LocationUpdateMode.RUNTIME -> toggleButton.isChecked = true
            LocationUpdateMode.SINGLE_UPDATE -> toggleButton.isChecked = false
        }
    }

    private fun setToggleBtnChangeListener(toggleButton: AppCompatToggleButton) {
        toggleButton.setOnCheckedChangeListener { button, isChecked ->
            if (isChecked) {
                sessionManager.locationUpdateMode = LocationUpdateMode.RUNTIME
                startTrackingService()
            } else {
                sessionManager.locationUpdateMode = LocationUpdateMode.SINGLE_UPDATE
                stopTrackingService()
                getLastKnowingLocation()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.actionLocationUpdateModeToggle -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        checkLocationPermissions()
        registerReceiver(locationUpdateReceiver, IntentFilter(LOCATION_UPDATES))
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(locationUpdateReceiver)
        if (!disposable.isDisposed) disposable.dispose()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTrackingService()
    }

    private val locationUpdateReceiver by lazy {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                intent?.getStringExtra(LOCATION_EXTRA)?.let { loadVenues(it) }
            }
        }
    }
}
