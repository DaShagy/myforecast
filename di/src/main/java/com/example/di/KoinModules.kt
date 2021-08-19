package com.example.di

import com.example.data.mapper.WeatherInfoMapperService
import com.example.data.repositories.WeatherInformationRepositoryImpl
import com.example.domain.repositories.WeatherInformationRepository
import com.example.domain.usecases.GetDailyWeatherByLatitudeAndLongitudeUseCase
import org.koin.dsl.module

val repositoriesModule = module {
    single { WeatherInfoMapperService() }
    single <WeatherInformationRepository> { WeatherInformationRepositoryImpl() }
}

val useCasesModule = module {
    single { GetDailyWeatherByLatitudeAndLongitudeUseCase() }
}