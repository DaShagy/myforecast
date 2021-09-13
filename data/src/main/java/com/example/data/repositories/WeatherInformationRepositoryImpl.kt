package com.example.data.repositories

import com.example.data.service.WeatherInformationService
import com.example.domain.entities.WeatherInformation
import com.example.domain.repositories.WeatherInformationRepository
import com.example.domain.util.Result

class WeatherInformationRepositoryImpl : WeatherInformationRepository {
    override fun getDailyWeatherByCity(
        city: String,
        getFromRemote: Boolean
    ): Result<WeatherInformation> {
        if (getFromRemote) {
            return WeatherInformationService().getDailyWeatherByCity(city)
        }
        return Result.Failure(Exception())
    }

    override fun getDailyWeatherByCoordinates(
        lat: String,
        lon: String,
        getFromRemote: Boolean,
    ): Result<WeatherInformation> {
        if (getFromRemote) {
            return WeatherInformationService().getDailyWeatherByCoordinates(lat, lon)
        }
        return Result.Failure(Exception())
    }
}