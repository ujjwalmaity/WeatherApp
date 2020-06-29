package dev.ujjwal.weatherapp.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import dev.ujjwal.weatherapp.R
import dev.ujjwal.weatherapp.util.PREF_USER_NAME
import dev.ujjwal.weatherapp.util.getUserDetails
import dev.ujjwal.weatherapp.view.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_current.view.*


class CurrentFragment : Fragment() {

    companion object {
        private val TAG = CurrentFragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_current, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.current_tv.text = context?.getUserDetails(PREF_USER_NAME)

        MainActivity.locationDetails.observe(viewLifecycleOwner, Observer {
            Log.i(TAG, "lat: ${it.latitude}")
            Log.i(TAG, "long: ${it.longitude}")
            view.current_tv.text = "latitude: ${it.latitude}\nlongitude: ${it.longitude}"
        })
    }
}