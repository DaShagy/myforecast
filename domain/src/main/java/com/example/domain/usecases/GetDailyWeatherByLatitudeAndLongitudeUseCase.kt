package com.example.domain.usecases

import com.example.domain.repositories.WeatherInformationRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class GetDailyWeatherByLatitudeAndLongitudeUseCase : KoinComponent {
    private val weatherInformationRepository: WeatherInformationRepository by inject()
    operator fun invoke(lat: Double, lon: Double, getFromRemote: Boolean) =
        weatherInformationRepository.getDailyWeatherByLatitudeAndLongitude(lat, lon, getFromRemote)
}