package com.example.myforecast.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entities.DayWeatherInformation
import com.example.domain.entities.WeatherInformation
import com.example.myforecast.R
import com.example.myforecast.adpaters.DailyWeatherInfoAdapter
import com.example.myforecast.databinding.ActivityMainBinding
import com.example.myforecast.utils.Data
import com.example.myforecast.utils.Event
import com.example.myforecast.utils.Status
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        viewModel.mainState.observe(::getLifecycle, ::updateUI)

        dayWeatherInfoAdapter = DailyWeatherInfoAdapter(this)

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

    private fun updateUI(weatherData: Event<Data<WeatherInformation>>) {
        when (weatherData.peekContent().responseType) {
            Status.ERROR -> {
                hideProgress()
                weatherData.peekContent().error?.message?.let { showMessage(this, it) }
            }
            Status.LOADING -> {
                showProgress()
            }
            Status.SUCCESSFUL -> {
                hideProgress()
                weatherData.peekContent().data?.let { setWeatherInfo(it) }
            }
        }
    }

    private fun setWeatherInfo(weatherInformation: WeatherInformation) {
        dayWeatherInfoAdapter.updateDataset(weatherInformation.daily as MutableList<DayWeatherInformation>)
        dayWeatherInfoAdapter.notifyDataSetChanged()
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