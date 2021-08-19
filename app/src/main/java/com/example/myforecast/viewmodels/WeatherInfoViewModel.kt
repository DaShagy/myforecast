package com.example.myforecast.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.WeatherInformation
import com.example.domain.usecases.GetDailyWeatherByLatitudeAndLongitudeUseCase
import com.example.domain.util.Result
import com.example.myforecast.utils.Data
import com.example.myforecast.utils.Event
import com.example.myforecast.utils.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherInfoViewModel(val getDailyWeatherByLatitudeAndLongitude: GetDailyWeatherByLatitudeAndLongitudeUseCase)
    : ViewModel() {

    private var _mainState: MutableLiveData<Event<Data<WeatherInformation>>> = MutableLiveData()
    val mainState: LiveData<Event<Data<WeatherInformation>>>
        get() {
            return _mainState
        }

    fun onRemoteSearch() = viewModelScope.launch {
        _mainState.value = Event(Data(responseType = Status.LOADING))
        when (val result = withContext(Dispatchers.IO) { getDailyWeatherByLatitudeAndLongitude(getFromRemote = true) }) {
            is Result.Failure -> {
                _mainState.value = Event(Data(responseType = Status.ERROR, error = result.exception))
            }
            is Result.Success -> {
                _mainState.value = Event(Data(responseType = Status.SUCCESSFUL, data = result.data))
            }
        }
    }
}