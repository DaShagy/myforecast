package com.example.myforecast.adpaters

import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entities.DayWeatherInformation
import com.example.myforecast.R
import com.example.myforecast.utils.toLocalDateTime
import com.google.android.material.card.MaterialCardView
import java.time.format.DateTimeFormatter

class DailyWeatherInfoAdapter(private val context: Context)
    : RecyclerView.Adapter<DailyWeatherInfoAdapter.DailyWeatherInfoViewHolder>() {

    private var dataset = mutableListOf<DayWeatherInformation>()

    fun updateDataset(data: MutableList<DayWeatherInformation>){
        dataset = data
    }

    class DailyWeatherInfoViewHolder(private val view: View)
        : RecyclerView.ViewHolder(view) {
        val cardView: MaterialCardView = view.findViewById(R.id.day_card)
        val dtTextView: TextView = view.findViewById(R.id.dt)
        val temperatureTextView: TextView = view.findViewById(R.id.temperature)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWeatherInfoViewHolder{
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_day_weather_info, parent, false)

        return DailyWeatherInfoViewHolder(adapterLayout)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DailyWeatherInfoViewHolder,
                                  position: Int) {
        val item = dataset[position]
        val itemCardView = holder.cardView
        val date = toLocalDateTime(item.dt)
        holder.dtTextView.text = date.dayOfWeek.toString()
        holder.temperatureTextView.text = "MAX/MIN: ${item.temp!!.max}/ ${item.temp!!.min}"

        itemCardView.setOnClickListener { showDayWeatherAlertDialog(item) }
    }

    override fun getItemCount()= dataset.size

    @RequiresApi(Build.VERSION_CODES.O)
    fun showDayWeatherAlertDialog(day: DayWeatherInformation){
        val formatter : DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")

        val message = "MAX: ${day.temp!!.max}\n" +
                "MIN: ${day.temp!!.min}\n" +
                "WEATHER: ${day.weather!![0].description}\n" +
                "SUNRISE TIME: ${toLocalDateTime(day.sunrise).format(formatter)}\n" +
                "SUNSET TIME: ${toLocalDateTime(day.sunset).format(formatter)}\n" +
                "WIND SPEED: ${day.windSpeed}"

        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder
            .setTitle(toLocalDateTime(day.dt).toString())
            .setMessage(message)
            .setNeutralButton("VOLVER") { dialogInterface: DialogInterface, i: Int -> }
            .show()
    }

}