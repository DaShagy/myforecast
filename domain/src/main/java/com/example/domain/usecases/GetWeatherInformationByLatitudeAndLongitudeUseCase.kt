package com.example.domain.usecases

import com.example.domain.repositories.WeatherInformationRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class GetWeatherInformationByLatitudeAndLongitudeUseCase : KoinComponent {
    private val weatherInformationRepository: WeatherInformationRepository by inject()
    operator fun invoke(lon: Double, lat: Double, getFromRemote: Boolean) =
        weatherInformationRepository.getDailyWeatherByLatitudeAndLongitude(lon, lat, getFromRemote)
}