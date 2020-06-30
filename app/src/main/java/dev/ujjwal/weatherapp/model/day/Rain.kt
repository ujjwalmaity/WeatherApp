package dev.ujjwal.weatherapp.model.day

import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("3h") val h3: Double
)