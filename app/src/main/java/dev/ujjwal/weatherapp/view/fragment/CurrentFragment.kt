package dev.ujjwal.weatherapp.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import dev.ujjwal.weatherapp.R
import dev.ujjwal.weatherapp.model.current.Current
import dev.ujjwal.weatherapp.model.unit
import dev.ujjwal.weatherapp.service.WeatherService
import dev.ujjwal.weatherapp.service.WeatherServiceBuilder
import dev.ujjwal.weatherapp.util.PREF_USER_NAME
import dev.ujjwal.weatherapp.util.getUserDetails
import dev.ujjwal.weatherapp.view.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_current.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CurrentFragment : Fragment() {

    companion object {
        private val TAG = CurrentFragment::class.java.simpleName
    }

    private var count = 0

    private lateinit var textView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_current, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textView = view.current_tv

        view.current_btn_refresh.setOnClickListener {
            updateUI()
        }
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun updateUI() {
        textView.text = "UserName: ${context?.getUserDetails(PREF_USER_NAME)}\n\nWeather: Loading..."
        count = 0
        MainActivity.locationDetails.observe(viewLifecycleOwner, Observer {
            Log.i(TAG, "lat: ${it.latitude}")
            Log.i(TAG, "long: ${it.longitude}")
            //textView.text = "latitude: ${it.latitude}\nlongitude: ${it.longitude}"
            if (count == 0) {
                fetchCurrentWeather(it.latitude.toString(), it.longitude.toString())
                count++
            }
        })
    }

    private fun fetchCurrentWeather(lat: String, lon: String) {
        val weatherService = WeatherServiceBuilder.buildService(WeatherService::class.java)

        val response = weatherService.getCurrentWeather(lat, lon, "0edc2e1ffef7f9d0db4ae60f34b8d147", unit)

        response.enqueue(object : Callback<Current> {
            override fun onFailure(call: Call<Current>, t: Throwable) {
            }

            override fun onResponse(call: Call<Current>, response: Response<Current>) {
                if (response.isSuccessful) {
                    Log.i(TAG, "TEMP: ${response.body()?.main?.temp}")
                    textView.text =
                        "UserName: ${context?.getUserDetails(PREF_USER_NAME)}\n\nWeather: ${response.body()?.weather?.get(0)?.description}\nTEMP: ${response.body()?.main?.temp}\nPlace: ${response.body()?.name}"
                }
            }
        })
    }
}