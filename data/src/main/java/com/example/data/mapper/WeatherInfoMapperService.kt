package com.example.data.mapper

import com.example.data.service.response.*
import com.example.domain.entities.*

open class WeatherInfoMapperService : BaseMapperRepository<WeatherInfoResponse, WeatherInformation> {
    override fun transform(type: WeatherInfoResponse): WeatherInformation =
        WeatherInformation(
            type.lat,
            type.lon,
            type.timezone,
            type.timezoneOffset,
            transformDailyWeatherList(type)
        )

    private fun transformDailyWeatherList(type: WeatherInfoResponse): List<DayWeatherInformation> {
        val dailyWeatherMapperService = DailyWeatherMapperService()
        return type.daily.map { dailyWeatherMapperService.transform(it) }
    }

    override fun transformToRepository(type: WeatherInformation): WeatherInfoResponse {
        TODO("Not yet implemented")
    }
}
class DailyWeatherMapperService : BaseMapperRepository<DaylyWeatherInfoResponse, DayWeatherInformation>{

    private val temperatureMapperService = TemperatureMapperService()
    private val dayTemperatureMapperService = DayTemperatureMapperService()

    override fun transform(type: DaylyWeatherInfoResponse): DayWeatherInformation =
        DayWeatherInformation(
            type.dt,
            type.sunrise,
            type.sunset,
            type.moonrise,
            type.moonset,
            type.moonPhase,
            dayTemperatureMapperService.transform(type.temp),
            temperatureMapperService.transform(type.feelsLike),
            type.pressure,
            type.humidity,
            type.dewPoint,
            type.windSpeed,
            type.windDeg,
            type.windGust,
            transformWeatherDetailList(type),
            type.clouds,
            type.pop,
            type.uvi
        )

    private fun transformWeatherDetailList(type: DaylyWeatherInfoResponse) : List<WeatherDetail>{
        val dayWeatherDetailMapperService = DayWeatherDetailMapperService()
        return type.weather.map { dayWeatherDetailMapperService.transform(it) }
    }

    override fun transformToRepository(type: DayWeatherInformation): DaylyWeatherInfoResponse {
        TODO("Not yet implemented")
    }
}

class TemperatureMapperService : BaseMapperRepository<TemperatureResponse, Temperature>{
    override fun transform(type: TemperatureResponse): Temperature =
        Temperature(
            type.day,
            type.night,
            type.eve,
            type.morn
        )

    override fun transformToRepository(type: Temperature): TemperatureResponse {
        TODO("Not yet implemented")
    }

}

class DayTemperatureMapperService : BaseMapperRepository<DayTemperatureResponse, DayTemperature>{
    override fun transform(type: DayTemperatureResponse): DayTemperature =
        DayTemperature(
            type.max,
            type.min,
            type.day,
            type.night,
            type.eve,
            type.morn
        )

    override fun transformToRepository(type: DayTemperature): DayTemperatureResponse {
        TODO("Not yet implemented")
    }

}

class DayWeatherDetailMapperService : BaseMapperRepository<WeatherDetailResponse, WeatherDetail>{
    override fun transform(type: WeatherDetailResponse): WeatherDetail =
        WeatherDetail(
            type.id,
            type.main,
            type.description,
            type.icon
        )

    override fun transformToRepository(type: WeatherDetail): WeatherDetailResponse {
        TODO("Not yet implemented")
    }

}

