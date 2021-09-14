package com.example.myforecast.utils

/**
 * A generic wrapper class around data request
 */

sealed class DataStatus<out T : Any> {
    class Successful<out T : Any>(val data: T) : DataStatus<T>()
    class Error(val error: Exception) : DataStatus<Nothing>()
    object Loading : DataStatus<Nothing>()
}
