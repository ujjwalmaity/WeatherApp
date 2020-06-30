package dev.ujjwal.weatherapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.ujjwal.weatherapp.R
import dev.ujjwal.weatherapp.model.unit
import dev.ujjwal.weatherapp.model.unitSymbol
import kotlinx.android.synthetic.main.fragment_setting.view.*

class SettingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.toggleButton.setOnCheckedChangeListener { _, isChecked ->
            unit = if (isChecked) "imperial" else "metric"
            unitSymbol = if (isChecked) "F" else "C"
        }
    }
}