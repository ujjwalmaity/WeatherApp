package dev.ujjwal.weatherapp.model.day

import com.google.gson.annotations.SerializedName

data class Day(
    @SerializedName("cod") val cod: Int,
    @SerializedName("message") val message: Int,
    @SerializedName("cnt") val cnt: Int,
    @SerializedName("list") val list: List<ListData>,
    @SerializedName("city") val city: City
)