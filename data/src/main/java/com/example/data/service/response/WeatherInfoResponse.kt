package com.example.data.service.response

import com.google.gson.annotations.SerializedName

class WeatherInfoResponse(
    val lat: Double,
    val lon: Double,
    val timezone: String,
    @SerializedName ("timezone_offset") val timezoneOffset: Int,
    val daily: List<DaylyWeatherInfoResponse>
)

class DaylyWeatherInfoResponse (
    val dt: Int,
    val sunrise: Int,
    val sunset: Int,
    val moonrise: Int,
    val moonset: Int,
    @SerializedName ("moon_phase") val moonPhase: Double,
    val temp: DayTemperatureResponse,
    @SerializedName ("feels_like") val feelsLike: TemperatureResponse,
    val pressure: Int,
    val humidity: Int,
    @SerializedName ("dew_point") val dewPoint: Double,
    @SerializedName ("wind_speed")val windSpeed: Double,
    @SerializedName ("wind_deg")val windDeg: Int,
    @SerializedName ("wind_gust")val windGust: Double,
    val weather: List<WeatherDetailResponse>,
    val clouds: Int,
    val pop: Double,
    val uvi: Double
)

class TemperatureResponse (
    val day: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)

class DayTemperatureResponse (
    val max: Double,
    val min: Double,
    val day: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)


class WeatherDetailResponse (
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

