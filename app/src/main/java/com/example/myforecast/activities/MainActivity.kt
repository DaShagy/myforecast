package com.example.myforecast.activities

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.domain.entities.DayWeatherInformation
import com.example.domain.entities.WeatherInformation
import com.example.myforecast.R
import com.example.myforecast.adpaters.DailyWeatherInfoAdapter
import com.example.myforecast.databinding.ActivityMainBinding
import com.example.myforecast.ui.alertdialogs.AskPermissionsAlertDialog
import com.example.myforecast.ui.alertdialogs.SearchByCityAlertDialog
import com.example.myforecast.ui.alertdialogs.WeatherDetailsAlertDialog
import com.example.myforecast.utils.Event
import com.example.myforecast.utils.DataStatus
import com.example.myforecast.utils.showMessage
import com.example.myforecast.viewmodels.WeatherInfoViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity(),
    SearchByCityAlertDialog.NoticeDialogListener,
    AskPermissionsAlertDialog.PermissionDialogListener{

    private lateinit var dayWeatherInfoAdapter: DailyWeatherInfoAdapter

    private val viewModel by viewModel<WeatherInfoViewModel>()

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val FINE_LOCATION_PERMISSION_CODE = 1


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

        val recyclerView = binding.root.recycler_view
        recyclerView.adapter = dayWeatherInfoAdapter
        recyclerView.setHasFixedSize(true)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        retrieveLocation()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.search_city_button -> { onSearchClicked() }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onSearchClicked() {
        val alertDialog = SearchByCityAlertDialog()
        alertDialog.show(supportFragmentManager, "search_by_city")
    }

    private fun updateUI(weatherData: Event<DataStatus<WeatherInformation>>) {
        when (val result = weatherData.peekContent()) {
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
        val alertDialog = WeatherDetailsAlertDialog(info)
        alertDialog.show(supportFragmentManager, "recycler_view_item")
    }

    private fun showProgress() {
        progress.visibility = View.VISIBLE
        recycler_view.visibility = View.GONE
    }

    private fun hideProgress() {
        progress.visibility = View.GONE
        recycler_view.visibility  = View.VISIBLE
    }

    override fun onDialogSearchClick(dialog: DialogFragment, dialogEditText: String) {
        viewModel.onRemoteSearch(dialogEditText)
    }

    fun onOpenAppSearch(location: Location){
        viewModel.onOpenAppSearch(location.latitude.toString(), location.longitude.toString())
    }

    private fun requestLocationPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            val alertDialog = AskPermissionsAlertDialog()
            alertDialog.show(supportFragmentManager, "permissions")
        } else {
            grantPermission()
        }
    }

    override fun onPermissionGrantedClick(dialog: DialogFragment) {
        grantPermission()
    }

    private fun grantPermission(){
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), FINE_LOCATION_PERMISSION_CODE)
        retrieveLocation()
    }

    private fun retrieveLocation(){
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED){

            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    location?.let {
                        onOpenAppSearch(it)
                    } ?: run {
                        showMessage(this@MainActivity, "No se pudo encontrar su localización")
                        onSearchClicked()
                    }
                }
        } else if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_DENIED){
            onSearchClicked()
        } else {
            requestLocationPermission()
        }
    }
}