package dev.ujjwal.weatherapp.model

class Location(lat: Double, long: Double) {
    var latitude: Double = lat
    var longitude: Double = long
}

//For temperature in Fahrenheit use units = imperial
//For temperature in Celsius use units = metric
var unit = "metric"
var unitSymbol = "C"
