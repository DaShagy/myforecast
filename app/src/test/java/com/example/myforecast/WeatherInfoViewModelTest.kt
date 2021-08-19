package com.example.myforecast

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.di.useCasesModule
import com.example.domain.entities.WeatherInformation
import com.example.domain.usecases.GetDailyWeatherByLatitudeAndLongitudeUseCase
import com.example.domain.util.Result
import com.example.myforecast.utils.Data
import com.example.myforecast.utils.Event
import com.example.myforecast.utils.Status
import com.example.myforecast.viewmodels.WeatherInfoViewModel
import com.google.common.truth.Truth
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.mockito.Mock
import org.mockito.Mockito.`when` as whenever
import org.mockito.MockitoAnnotations

class WeatherInfoViewModelTest : AutoCloseKoinTest() {

    @ObsoleteCoroutinesApi
    private var mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var subject: WeatherInfoViewModel
    @Mock lateinit var weatherInfoValidResult: Result.Success<WeatherInformation>
    @Mock lateinit var weatherInfoInvalidResult: Result.Failure
    @Mock lateinit var weatherInfo: WeatherInformation
    @Mock lateinit var exception: Exception

    private val getDailyWeatherByLatitudeAndLongitude :
            GetDailyWeatherByLatitudeAndLongitudeUseCase by inject()

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        startKoin {
            modules(listOf(useCasesModule))
        }

        declareMock<GetDailyWeatherByLatitudeAndLongitudeUseCase>()
        MockitoAnnotations.initMocks(this)
        subject = WeatherInfoViewModel(getDailyWeatherByLatitudeAndLongitude)
    }

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @After
    fun after() {
        stopKoin()
        mainThreadSurrogate.close()
        Dispatchers.resetMain()
    }

    @Test
    fun onSearchRemoteTestSuccessful() {
        val loadingStatusEvent : Event<Data<WeatherInformation>> = Event(Data(Status.LOADING))
        val successStatusEvent : Event<Data<WeatherInformation>> = Event(Data(Status.SUCCESSFUL, data = weatherInfo))

        val liveDataUnderTest = subject.mainState.testObserver()
        whenever(getDailyWeatherByLatitudeAndLongitude.invoke(true)).thenReturn(weatherInfoValidResult)
        whenever(weatherInfoValidResult.data).thenReturn(weatherInfo)
        runBlocking {
            subject.onRemoteSearch().join()
        }
        //Truth.assertThat(liveDataUnderTest.observedValues)
        //    .isEqualTo(listOf(Event(Data(Status.LOADING)), Event(Data(Status.SUCCESSFUL, data = weatherInfo))))
        //Truth.assertThat(liveDataUnderTest.observedValues[0]).isEqualTo(loadingStatusEvent)
        assertEquals(loadingStatusEvent.peekContent().responseType, liveDataUnderTest.observedValues[0]!!.peekContent().responseType)
    }

    class TestObserver<T> : Observer<T> {
        val observedValues = mutableListOf<T?>()
        override fun onChanged(value: T?) {
            observedValues.add(value)
        }
    }

    private fun <T> LiveData<T>.testObserver() = TestObserver<T>().also {
        observeForever(it)
    }


}
