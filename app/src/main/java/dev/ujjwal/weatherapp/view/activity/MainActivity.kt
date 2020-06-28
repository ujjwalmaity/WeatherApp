package dev.ujjwal.weatherapp.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import dev.ujjwal.weatherapp.R
import dev.ujjwal.weatherapp.view.adapter.SectionsPagerAdapter
import dev.ujjwal.weatherapp.view.fragment.CurrentFragment
import dev.ujjwal.weatherapp.view.fragment.DateFragment
import dev.ujjwal.weatherapp.view.fragment.DaysFragment
import dev.ujjwal.weatherapp.view.fragment.SettingFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager: ViewPager = findViewById(R.id.view_pager)
        setupViewPager(viewPager)

        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = SectionsPagerAdapter(supportFragmentManager)
        adapter.addFragment(CurrentFragment(), "Current")
        adapter.addFragment(DateFragment(), "Date")
        adapter.addFragment(DaysFragment(), "30 Days")
        adapter.addFragment(SettingFragment(), "Setting")
        viewPager.adapter = adapter
    }
}
