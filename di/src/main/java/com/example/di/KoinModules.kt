package com.example.di

import com.example.domain.usecases.GetDailyWeatherByLatitudeAndLongitudeUseCase
import org.koin.dsl.module

val repositoriesModule = module {  }

val useCasesModule = module {
    single { GetDailyWeatherByLatitudeAndLongitudeUseCase() }
}