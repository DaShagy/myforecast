package com.example.data.service

import com.example.data.OpenWeatherRequestGenerator
import com.example.data.mapper.WeatherInfoMapperService
import com.example.data.service.api.OpenWeatherApi
import com.example.data.service.response.Coordinates
import com.example.domain.entities.WeatherInformation
import com.example.domain.util.Result

class WeatherInformationService {

    private val api: OpenWeatherRequestGenerator = OpenWeatherRequestGenerator()
    private val infoMapper: WeatherInfoMapperService = WeatherInfoMapperService()

    fun getDailyWeatherByCoordinates(coord: Coordinates): Result<WeatherInformation> {

        val filter = HashMap<String, String>()
        filter["lat"] = coord.lat.toString()
        filter["lon"] = coord.lon.toString()
        filter["exclude"] = "current,minutely,hourly,alerts"
        filter["units"] = "metric"

        val callResponse = api.createService(OpenWeatherApi::class.java).getDailyWeatherByCoordinates(filter)
        val response = callResponse.execute()
        if (response != null) {
            if (response.isSuccessful) {
                response.body()?.let { infoMapper.transform(it) }?.let { return Result.Success(it) }
            }
            return Result.Failure(Exception(response.message()))
        }
        return Result.Failure(Exception("Bad request/response"))
    }

    fun getDailyWeatherByCity(city: String): Result<WeatherInformation> {

        val callResponse = api.createService(OpenWeatherApi::class.java).getDailyWeatherByCity(city)
        val response = callResponse.execute()
        if (response != null) {
            if (response.isSuccessful) {
                response.body()?.let { return getDailyWeatherByCoordinates(it.coord) }
            }
            return Result.Failure(Exception(response.message()))
        }
        return Result.Failure(Exception("Bad request/response"))
    }

}