package com.example.data.service.api

import com.example.data.service.response.WeatherInfoResponse
import retrofit2.Call
import retrofit2.http.GET

interface OpenWeatherApi {
    @GET("/data/2.5/onecall")
    fun getDailyWeatherByLatitudeAndLongitude(): Call<WeatherInfoResponse>
}