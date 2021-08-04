package com.example.domain.entities

const val DEFAULT_DOUBLE = 0.0
const val DEFAULT_INT = 0
const val NOT_FOUND = "Not Found"


class WeatherInformation {
    val lat: Double = DEFAULT_DOUBLE
    val lon: Double = DEFAULT_DOUBLE
    val timeZone: String = NOT_FOUND
    val timeZoneOffset: Int = DEFAULT_INT
    val daily: List<DayWeatherInformation>? = null
}

class DayWeatherInformation {
    val dt: Int = DEFAULT_INT
    val sunrise: Int = DEFAULT_INT
    val sunset: Int = DEFAULT_INT
    val moonrise: Int = DEFAULT_INT
    val moonset: Int = DEFAULT_INT
    val moonphase: Double = DEFAULT_DOUBLE
    val temp: Temperature? = null
    val feelsLike: Temperature? = null
    val pressure: Int = DEFAULT_INT
    val humidity: Int = DEFAULT_INT
    val dewPoint: Double = DEFAULT_DOUBLE
    val windSpeed: Double = DEFAULT_DOUBLE
    val windDeg: Int = DEFAULT_INT
    val windGust: Double = DEFAULT_DOUBLE
    val weather: Weather? = null
    val clouds: Int = DEFAULT_INT
    val pop: Int = DEFAULT_INT
    val uvi: Double = DEFAULT_DOUBLE
}

class Temperature {
    val day: Double = DEFAULT_DOUBLE
    val min: Double = DEFAULT_DOUBLE
    val max: Double = DEFAULT_DOUBLE
    val night: Double = DEFAULT_DOUBLE
    val eve: Double = DEFAULT_DOUBLE
    val mor: Double = DEFAULT_DOUBLE
}

class Weather {
    val id: Int = DEFAULT_INT
    val main: String = NOT_FOUND
    val desc: String = NOT_FOUND
    val icon: String = NOT_FOUND
}