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
import coil.load
import com.example.domain.entities.DayWeatherInformation
import com.example.myforecast.R
import com.example.myforecast.databinding.ActivityMainBinding
import com.example.myforecast.databinding.ListDayWeatherInfoBinding
import com.example.myforecast.utils.toLocalDateTime
import com.google.android.material.card.MaterialCardView
import java.time.format.DateTimeFormatter

class DailyWeatherInfoAdapter(private val listener: (DayWeatherInformation) -> Unit)
    : RecyclerView.Adapter<DailyWeatherInfoAdapter.DailyWeatherInfoViewHolder>() {

    private var dataset = mutableListOf<DayWeatherInformation>()

    fun updateDataset(data: MutableList<DayWeatherInformation>){
        dataset = data
    }

    class DailyWeatherInfoViewHolder(val binding: ListDayWeatherInfoBinding,
                                     val onItemClicked: (Int) -> Unit)
        : RecyclerView.ViewHolder(binding.root) {
            init {
                binding.root.setOnClickListener {
                    onItemClicked(absoluteAdapterPosition)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWeatherInfoViewHolder{

        val binding = ListDayWeatherInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DailyWeatherInfoViewHolder(binding){
            listener(dataset[it])
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DailyWeatherInfoViewHolder,
                                  position: Int) {
        with (holder){
            with (dataset[position]){
                binding.temperature.text = "${this.temp!!.max}°C/ ${this.temp!!.min}°C"
                binding.day.text = toLocalDateTime(this.dt).dayOfWeek.toString()
                binding.weatherIcon.load("http://openweathermap.org/img/wn/${this.weather!![0].icon}@2x.png")
            }
        }
    }

    override fun getItemCount()= dataset.size
}