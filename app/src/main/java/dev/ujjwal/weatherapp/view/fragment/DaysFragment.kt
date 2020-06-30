package dev.ujjwal.weatherapp.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dev.ujjwal.weatherapp.R
import dev.ujjwal.weatherapp.model.day.Day
import dev.ujjwal.weatherapp.model.unit
import dev.ujjwal.weatherapp.service.WeatherService
import dev.ujjwal.weatherapp.service.WeatherServiceBuilder
import dev.ujjwal.weatherapp.util.PREF_USER_NAME
import dev.ujjwal.weatherapp.util.getUserDetails
import dev.ujjwal.weatherapp.view.activity.MainActivity
import dev.ujjwal.weatherapp.view.adapter.ItemListDayAdapter
import kotlinx.android.synthetic.main.fragment_days.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DaysFragment : Fragment() {

    companion object {
        private val TAG = DaysFragment::class.java.simpleName
    }

    private lateinit var textView: TextView

    private var count = 0

    private val itemListDayAdapter: ItemListDayAdapter = ItemListDayAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_days, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textView = view.days_tv_usr_name

        view.recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = itemListDayAdapter
        }

        view.days_refresh.setOnClickListener {
            updateUI()
        }
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }


    private fun updateUI() {
        textView.text = "UserName: ${context?.getUserDetails(PREF_USER_NAME)}\nWeather: Loading..."
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

        val response = weatherService.get5Day3HourWeather(lat, lon, "0edc2e1ffef7f9d0db4ae60f34b8d147", unit)

        response.enqueue(object : Callback<Day> {
            override fun onFailure(call: Call<Day>, t: Throwable) {
            }

            override fun onResponse(call: Call<Day>, response: Response<Day>) {
                if (response.isSuccessful) {
                    itemListDayAdapter.updateItemListDay(response.body()!!.list)
                    textView.text = "UserName: ${context?.getUserDetails(PREF_USER_NAME)}\nPlace: ${response.body()?.city?.name}"
                }
            }
        })
    }
}