package dev.ujjwal.weatherapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.ujjwal.weatherapp.R
import dev.ujjwal.weatherapp.util.PREF_USER_NAME
import dev.ujjwal.weatherapp.util.getUserDetails
import kotlinx.android.synthetic.main.fragment_current.view.*

class CurrentFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_current, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.current_tv.text = context?.getUserDetails(PREF_USER_NAME)
    }
}