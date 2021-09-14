package com.example.data.service.response

class OpenWeatherBaseResponse<T>(

    var code: Int,
    var status: String,
    var data: T?
)