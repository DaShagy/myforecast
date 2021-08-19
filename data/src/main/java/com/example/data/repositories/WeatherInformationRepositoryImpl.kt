package com.example.data.repositories

import com.example.data.service.WeatherInformationService
import com.example.domain.entities.WeatherInformation
import com.example.domain.repositories.WeatherInformationRepository
import com.example.domain.util.Result

class WeatherInformationRepositoryImpl : WeatherInformationRepository {
    override fun getDailyWeatherByLatitudeAndLongitude(
        getFromRemote: Boolean
    ): Result<WeatherInformation> {
        if (getFromRemote) {
            return WeatherInformationService().getDailyWeatherByLatitudeAndLongitude()
        }
        return Result.Failure(Exception())
    }
}