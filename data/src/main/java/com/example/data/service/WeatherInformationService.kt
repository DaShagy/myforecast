package com.example.data.service

import com.example.data.OpenWeatherRequestGenerator
import com.example.data.mapper.WeatherInfoMapperService
import com.example.data.service.api.OpenWeatherApi
import com.example.domain.entities.WeatherInformation
import com.example.domain.util.Result

class WeatherInformationService {

    private val api: OpenWeatherRequestGenerator = OpenWeatherRequestGenerator()
    private val infoMapper: WeatherInfoMapperService = WeatherInfoMapperService()

    fun getDailyWeatherByLatitudeAndLongitude() : Result<WeatherInformation> {
        val callResponse = api.createService(OpenWeatherApi::class.java).getDailyWeatherByLatitudeAndLongitude()
        val response = callResponse.execute()
        if (response != null) {
            if (response.isSuccessful) {
                response.body()?.let { infoMapper.transform(it) }?.let { return Result.Success(it) }
            }
            return Result.Failure(Exception(response.message()))
        }
        return Result.Failure(Exception("Bad request/response"))
    }

}