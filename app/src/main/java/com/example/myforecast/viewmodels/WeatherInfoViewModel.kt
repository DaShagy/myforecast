package com.example.myforecast.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.WeatherInformation
import com.example.domain.usecases.GetDailyWeatherByCityUseCase
import com.example.domain.util.Result
import com.example.myforecast.utils.Event
import com.example.myforecast.utils.DataStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherInfoViewModel(val getDailyWeatherByCity: GetDailyWeatherByCityUseCase)
    : ViewModel() {

    private var _mainState: MutableLiveData<Event<DataStatus<WeatherInformation>>> = MutableLiveData()
    val mainState: LiveData<Event<DataStatus<WeatherInformation>>>
        get() {
            return _mainState
        }

    fun onRemoteSearch(city: String) = viewModelScope.launch {
        _mainState.value = Event(DataStatus.Loading)
        when (val result = withContext(Dispatchers.IO) { getDailyWeatherByCity(city = city, getFromRemote = true) }) {
            is Result.Failure -> {
                _mainState.value = Event(DataStatus.Error(error = result.exception))
            }
            is Result.Success -> {
                _mainState.value = Event(DataStatus.Successful(data = result.data))
            }
        }
    }
}