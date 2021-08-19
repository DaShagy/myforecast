package com.example.domain.entities

const val DEFAULT_DOUBLE = 0.0
const val DEFAULT_INT = 0
const val NOT_FOUND = "Not Found"


class WeatherInformation(
    val lat: Double = DEFAULT_DOUBLE,
    val lon: Double = DEFAULT_DOUBLE,
    val timezone: String = NOT_FOUND,
    val timezoneOffset: Int = DEFAULT_INT,
    val daily: List<DayWeatherInformation>? = null
)

class DayWeatherInformation (
    val dt: Int = DEFAULT_INT,
    val sunrise: Int = DEFAULT_INT,
    val sunset: Int = DEFAULT_INT,
    val moonrise: Int = DEFAULT_INT,
    val moonset: Int = DEFAULT_INT,
    val moonPhase: Double = DEFAULT_DOUBLE,
    val temp: DayTemperature? = null,
    val feelsLike: Temperature? = null,
    val pressure: Int = DEFAULT_INT,
    val humidity: Int = DEFAULT_INT,
    val dewPoint: Double = DEFAULT_DOUBLE,
    val windSpeed: Double = DEFAULT_DOUBLE,
    val windDeg: Int = DEFAULT_INT,
    val windGust: Double = DEFAULT_DOUBLE,
    val weather: List<WeatherDetail>? = null,
    val clouds: Int = DEFAULT_INT,
    val pop: Int = DEFAULT_INT,
    val uvi: Double = DEFAULT_DOUBLE
)

open class Temperature (
    val day: Double = DEFAULT_DOUBLE,
    val night: Double = DEFAULT_DOUBLE,
    val eve: Double = DEFAULT_DOUBLE,
    val morn: Double = DEFAULT_DOUBLE
)

class DayTemperature (
    val max: Double = DEFAULT_DOUBLE,
    val min: Double = DEFAULT_DOUBLE,
    day: Double = DEFAULT_DOUBLE,
    night: Double = DEFAULT_DOUBLE,
    eve: Double = DEFAULT_DOUBLE,
    morn: Double = DEFAULT_DOUBLE
): Temperature(day, night, eve, morn)

class WeatherDetail (
    val id: Int = DEFAULT_INT,
    val main: String = NOT_FOUND,
    val description: String = NOT_FOUND,
    val icon: String = NOT_FOUND
)