package dev.ujjwal.weatherapp.view.activity

import android.Manifest
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.PendingResult
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResult
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.material.tabs.TabLayout
import dev.ujjwal.weatherapp.R
import dev.ujjwal.weatherapp.model.Location
import dev.ujjwal.weatherapp.view.adapter.SectionsPagerAdapter
import dev.ujjwal.weatherapp.view.fragment.CurrentFragment
import dev.ujjwal.weatherapp.view.fragment.DateFragment
import dev.ujjwal.weatherapp.view.fragment.DaysFragment
import dev.ujjwal.weatherapp.view.fragment.SettingFragment
import dev.ujjwal.weatherapp.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
        private const val MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
        private const val REQUEST_CHECK_SETTINGS = 199
        var locationDetails: MutableLiveData<Location> = MutableLiveData()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager: ViewPager = findViewById(R.id.view_pager)
        setupViewPager(viewPager)

        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.initLocation()
        viewModel.locationDetails.observe(this, Observer {
            Log.i(TAG, "lat: ${it.latitude}")
            Log.i(TAG, "long: ${it.longitude}")
            locationDetails.value = it
        })

        checkPermission()
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = SectionsPagerAdapter(supportFragmentManager)
        adapter.addFragment(CurrentFragment(), "Current")
        adapter.addFragment(DateFragment(), "Date")
        adapter.addFragment(DaysFragment(), "5 Day / 3 Hour")
        adapter.addFragment(SettingFragment(), "Setting")
        viewPager.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        enableGps()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopLocationUpdates()
    }

    private fun enableGps() {
        val googleApiClient = GoogleApiClient.Builder(this).addApi(LocationServices.API).build()
        googleApiClient.connect()
        val builder = LocationSettingsRequest.Builder().addLocationRequest(viewModel.locationRequest)
        builder.setAlwaysShow(true)
        val result: PendingResult<LocationSettingsResult> = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())
        result.setResultCallback { result ->
            val status: Status = result.status
            when (status.statusCode) {
                LocationSettingsStatusCodes.SUCCESS ->
                    //Log.i(TAG, "All location settings are satisfied.");
                    try {
                        viewModel.startLocationUpdates()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->
                    //Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");
                    try {
                        // Show the dialog by calling startResolutionForResult(), and check the result
                        // in onActivityResult().
                        status.startResolutionForResult(this, REQUEST_CHECK_SETTINGS)
                    } catch (e: IntentSender.SendIntentException) {
                        //Log.i(TAG, "PendingIntent unable to execute request.");
                    }
                LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                }
            }
        }
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
            return
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Log.i(TAG, "Permission was granted for Location")
                    viewModel.startLocationUpdates()
                } else {
                    Log.i(TAG, "Permission was denied for Location")
                    checkPermission()
                }
                return
            }
        }
    }
}
