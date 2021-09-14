package com.example.data.mapper

import com.example.data.service.response.*
import com.example.domain.entities.*

class WeatherInfoMapperService : BaseMapperRepository<WeatherInfoResponse, WeatherInformation> {
    override fun transform(type: WeatherInfoResponse): WeatherInformation =
        WeatherInformation(
            type.lat,
            type.lon,
            type.timezone,
            type.timezoneOffset,
            transformDailyWeatherList(type)
        )

    private fun transformDailyWeatherList(type: WeatherInfoResponse): List<DayWeatherInformation> {
        return type.daily.map { DailyWeatherMapperService().transform(it) }
    }

    override fun transformToRepository(type: WeatherInformation): WeatherInfoResponse {
        TODO("Not yet implemented")
    }
}
class DailyWeatherMapperService : BaseMapperRepository<DailyWeatherInfoResponse, DayWeatherInformation>{

    override fun transform(type: DailyWeatherInfoResponse): DayWeatherInformation =
        DayWeatherInformation(
            type.dt,
            type.sunrise,
            type.sunset,
            type.moonrise,
            type.moonset,
            type.moonPhase,
            DayTemperatureMapperService().transform(type.temp),
            TemperatureMapperService().transform(type.feelsLike),
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

    private fun transformWeatherDetailList(type: DailyWeatherInfoResponse) : List<WeatherDetail>{
        val dayWeatherDetailMapperService = DayWeatherDetailMapperService()
        return type.weather.map { dayWeatherDetailMapperService.transform(it) }
    }

    override fun transformToRepository(type: DayWeatherInformation): DailyWeatherInfoResponse {
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
            TemperatureMapperService().transform(
                TemperatureResponse(
                    type.day,
                    type.night,
                    type.eve,
                    type.morn
                )
            )
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

