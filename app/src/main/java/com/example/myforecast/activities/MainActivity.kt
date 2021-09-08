package com.example.myforecast.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import com.example.domain.entities.DayWeatherInformation
import com.example.domain.entities.WeatherInformation
import com.example.myforecast.R
import com.example.myforecast.adpaters.DailyWeatherInfoAdapter
import com.example.myforecast.databinding.ActivityMainBinding
import com.example.myforecast.utils.Event
import com.example.myforecast.utils.DataStatus
import com.example.myforecast.utils.showDayWeatherAlertDialog
import com.example.myforecast.utils.showMessage
import com.example.myforecast.viewmodels.WeatherInfoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var dayWeatherInfoAdapter: DailyWeatherInfoAdapter

    private val viewModel by viewModel<WeatherInfoViewModel>()

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        viewModel.mainState.observe(::getLifecycle, ::updateUI)

        dayWeatherInfoAdapter = DailyWeatherInfoAdapter {
            dayWeatherInformation -> (
                onClickedRecyclerViewItem(dayWeatherInformation)
                )
        }

        var recyclerView = binding.root.recycler_view
        recyclerView.adapter = dayWeatherInfoAdapter
        recyclerView.setHasFixedSize(true)

        onSearchClicked()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.refresh_button -> { onSearchClicked() }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onSearchClicked() {
        viewModel.onRemoteSearch()
    }

    private fun updateUI(weatherData: Event<DataStatus<WeatherInformation>>) {
        var result = weatherData.peekContent()
        when (result) {
            is DataStatus.Error -> {
                hideProgress()
                result.error.message.let {
                    showMessage(this, it ?: "No se pudo recuperar el mensaje de error")
                }
            }
            is DataStatus.Loading -> {
                showProgress()
            }
            is DataStatus.Successful -> {
                hideProgress()
                setWeatherInfo(result.data)
            }
        }
    }

    private fun setWeatherInfo(weatherInformation: WeatherInformation) {
        dayWeatherInfoAdapter.updateDataset(weatherInformation.daily as MutableList<DayWeatherInformation>)
        dayWeatherInfoAdapter.notifyDataSetChanged()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onClickedRecyclerViewItem(info: DayWeatherInformation){
        showDayWeatherAlertDialog(info, this)
    }

    private fun showProgress() {
        progress.visibility = View.VISIBLE
        recycler_view.visibility = View.GONE
    }

    private fun hideProgress() {
        progress.visibility = View.GONE
        recycler_view.visibility  = View.VISIBLE
    }
}