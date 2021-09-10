package com.example.di

import com.example.data.mapper.*
import com.example.data.repositories.WeatherInformationRepositoryImpl
import com.example.domain.repositories.WeatherInformationRepository
import com.example.domain.usecases.GetDailyWeatherByCityUseCase
import org.koin.dsl.module

val repositoriesModule = module {
    single { WeatherInfoMapperService() }
    single { DailyWeatherMapperService() }
    single { TemperatureMapperService() }
    single { DayTemperatureMapperService() }
    single { DayWeatherDetailMapperService() }
    single <WeatherInformationRepository> { WeatherInformationRepositoryImpl() }
}

val useCasesModule = module {
    single { GetDailyWeatherByCityUseCase() }
}