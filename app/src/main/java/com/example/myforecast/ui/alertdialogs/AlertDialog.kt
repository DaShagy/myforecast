package com.example.myforecast.ui.alertdialogs

import android.content.Context

interface AlertDialog {
    fun buildAndShowAlertDialog(item: Any, context: Context)
}