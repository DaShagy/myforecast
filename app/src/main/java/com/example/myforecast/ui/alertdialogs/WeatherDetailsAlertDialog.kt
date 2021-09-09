package com.example.myforecast.ui.alertdialogs

import android.content.Context
import android.content.DialogInterface
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.domain.entities.DayWeatherInformation
import com.example.myforecast.utils.toLocalDateTime
import java.time.format.DateTimeFormatter

class WeatherDetailsAlertDialog : AlertDialog {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun buildAndShowAlertDialog(day: Any, context: Context) {
        if (day is DayWeatherInformation) {
            val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
            val dayFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("EE dd/MMM")

            val title = toLocalDateTime(day.dt).format(dayFormatter)

            val message = "WEATHER: ${day.weather!![0].description}\n" +
                    "SUNRISE TIME: ${toLocalDateTime(day.sunrise).format(timeFormatter)}\n" +
                    "SUNSET TIME: ${toLocalDateTime(day.sunset).format(timeFormatter)}\n" +
                    "WIND SPEED: ${day.windSpeed}"

            val alertDialogBuilder = androidx.appcompat.app.AlertDialog.Builder(context)
            alertDialogBuilder
                .setTitle(title)
                .setMessage(message)
                .setNeutralButton("VOLVER") { _: DialogInterface, _: Int -> }
                .show()
        }
    }
}