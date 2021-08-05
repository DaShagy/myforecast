package com.example.domain.repositories

import com.example.domain.entities.WeatherInformation
import com.example.domain.util.Result

interface WeatherInformationRepository {
    fun getDailyWeatherByLatitudeAndLongitude(lat: Double,
                                              lon: Double,
                                              getFromRemote: Boolean)
    : Result<WeatherInformation>
}