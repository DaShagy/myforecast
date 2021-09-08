package com.example.myforecast.utils

import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.example.domain.entities.DayWeatherInformation
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun showMessage(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

@RequiresApi(Build.VERSION_CODES.O)
fun toLocalDateTime(dt: Int): LocalDateTime {
    return Instant.ofEpochSecond(dt.toLong()).atZone(ZoneId.systemDefault()).toLocalDateTime()
}

@RequiresApi(Build.VERSION_CODES.O)
fun showDayWeatherAlertDialog(day: DayWeatherInformation, context: Context){
    val timeFormatter : DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    val dayFormatter  : DateTimeFormatter = DateTimeFormatter.ofPattern("EE dd/MMM")

    val title = toLocalDateTime(day.dt).format(dayFormatter)

    val message = "WEATHER: ${day.weather!![0].description}\n" +
            "SUNRISE TIME: ${toLocalDateTime(day.sunrise).format(timeFormatter)}\n" +
            "SUNSET TIME: ${toLocalDateTime(day.sunset).format(timeFormatter)}\n" +
            "WIND SPEED: ${day.windSpeed}"

    val alertDialogBuilder = AlertDialog.Builder(context)
    alertDialogBuilder
        .setTitle(title)
        .setMessage(message)
        .setNeutralButton("VOLVER") { _: DialogInterface, _: Int -> }
        .show()
}
