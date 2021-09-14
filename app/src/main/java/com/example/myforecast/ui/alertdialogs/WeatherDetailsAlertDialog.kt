package com.example.myforecast.ui.alertdialogs

import android.app.Dialog

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.domain.entities.DayWeatherInformation
import com.example.myforecast.utils.toLocalDateTime
import java.time.format.DateTimeFormatter

class WeatherDetailsAlertDialog(private val day: DayWeatherInformation) : DialogFragment(){
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let{
            val builder = AlertDialog.Builder(it)

            val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
            val dayFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("EE dd/MMM")

            val title = toLocalDateTime(day.dt).format(dayFormatter)

            val message = "WEATHER: ${day.weather!![0].description}\n" +
                    "SUNRISE TIME: ${toLocalDateTime(day.sunrise).format(timeFormatter)}\n" +
                    "SUNSET TIME: ${toLocalDateTime(day.sunset).format(timeFormatter)}\n" +
                    "WIND SPEED: ${day.windSpeed}"

            builder.setTitle(title)
                .setMessage(message)
                .create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }
}