package com.example.domain.usecases

import com.example.domain.repositories.WeatherInformationRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class GetDailyWeatherByCityUseCase : KoinComponent {
    private val weatherInformationRepository: WeatherInformationRepository by inject()
    operator fun invoke(city: String, getFromRemote: Boolean) =
        weatherInformationRepository.getDailyWeatherByCity(city, getFromRemote)
}