package com.example.myforecast.di

import com.example.myforecast.viewmodels.WeatherInfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { WeatherInfoViewModel(get()) }
}