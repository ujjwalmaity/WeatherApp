package dev.ujjwal.weatherapp.viewmodel

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.*
import dev.ujjwal.weatherapp.model.Location

class MainViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }

    private var fusedLocationProviderClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(application)
    lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    var locationDetails: MutableLiveData<Location> = MutableLiveData()

    fun initLocation() {
        locationRequest = LocationRequest.create()
        locationRequest.interval = 2000
        locationRequest.priority = LocationRequest.PRIORITY_LOW_POWER
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (loc in locationResult.locations) {
                    locationDetails.value = Location(loc.latitude, loc.longitude)
                    Log.i(TAG, "lat: ${loc.latitude}")
                    Log.i(TAG, "long: ${loc.longitude}")
                }
            }
        }
    }

    fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    fun stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }
}
