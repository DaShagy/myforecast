package com.example.data.service.response

data class CityWeatherResponse (
    val coord: Coordinates
        )

data class Coordinates (
    val lat: Double,
    val lon: Double
        )