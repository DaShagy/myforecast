package com.example.data

import android.util.Log
import com.chuckerteam.chucker.api.ChuckerInterceptor
import io.realm.internal.SyncObjectServerFacade.getApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val LATITUDE_KEY_ARG = "lat"
private const val LATITUDE_KEY_ARG_VALUE = "-26.824141"
private const val LONGITUDE_KEY_ARG = "lon"
private const val LONGITUDE_KEY_ARG_VALUE = "-65.222603"
private const val TO_EXCLUDE_KEY_ARG = "exclude"
private const val TO_EXCLUDE_KEY_ARG_VALUE = "current,minutely,hourly,alerts"
private const val UNITS_KEY_ARG = "units"
private const val UNITS_KEY_ARG_VALUE = "metric"
private const val PUBLIC_API_KEY_ARG = "appid"
private const val PUBLIC_API_KEY_ARG_VALUE = "077963ca38c545cea1f562582d62abeb"
private const val OPEN_WEATHER_BASE_URL = "https://api.openweathermap.org/"
private const val MAX_TRYOUTS = 3
private const val INIT_TRYOUT = 1

class OpenWeatherRequestGenerator {

    private val context = getApplicationContext()

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .addInterceptor(ChuckerInterceptor(context))
        .addInterceptor{ chain ->
            val defaultRequest = chain.request()

            val defaultHttpUrl = defaultRequest.url()

            val httpUrl = defaultHttpUrl.newBuilder()
                .addQueryParameter(LATITUDE_KEY_ARG, LATITUDE_KEY_ARG_VALUE)
                .addQueryParameter(LONGITUDE_KEY_ARG, LONGITUDE_KEY_ARG_VALUE)
                .addQueryParameter(TO_EXCLUDE_KEY_ARG, TO_EXCLUDE_KEY_ARG_VALUE)
                .addQueryParameter(UNITS_KEY_ARG, UNITS_KEY_ARG_VALUE)
                .addQueryParameter(PUBLIC_API_KEY_ARG, PUBLIC_API_KEY_ARG_VALUE)
                .build()

            val requestBuilder = defaultRequest.newBuilder()
                .url(httpUrl)

            chain.proceed(requestBuilder.build())
        }
        .addInterceptor { chain ->
            val request = chain.request()
            var response = chain.proceed(request)
            var tryOuts = INIT_TRYOUT

            while (!response.isSuccessful && tryOuts < MAX_TRYOUTS) {
                Log.d(
                    this@OpenWeatherRequestGenerator.javaClass.simpleName, "intercept: timeout/connection failure, " +
                            "performing automatic retry ${(tryOuts + 1)}"
                )
                tryOuts++
                response = chain.proceed(request)
            }

            response
        }

    private val builder = Retrofit.Builder()
        .baseUrl(OPEN_WEATHER_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

    fun <S> createService(serviceClass: Class<S>): S {
        val retrofit = builder.client(httpClient.build()).build()
        return retrofit.create(serviceClass)
    }
}