package dev.ujjwal.weatherapp.model.day

import com.google.gson.annotations.SerializedName

data class Sys(
    @SerializedName("pod") val pod: String
)