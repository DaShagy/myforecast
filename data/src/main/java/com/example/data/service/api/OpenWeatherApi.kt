package com.example.data.service.api

import com.example.data.service.response.CityWeatherResponse
import com.example.data.service.response.WeatherInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface OpenWeatherApi {
    @GET("/data/2.5/weather")
    fun getDailyWeatherByCity(@Query("q") city: String): Call<CityWeatherResponse>

    @GET("/data/2.5/onecall")
    fun getDailyWeatherByCoordinates(@QueryMap filter: HashMap<String, String>): Call<WeatherInfoResponse>
}