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
