package com.kodiiiofc.urbanuniversity.weather

import android.location.LocationManager
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodiiiofc.urbanuniversity.weather.models.CurrentWeather
import com.kodiiiofc.urbanuniversity.weather.utils.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MainViewModel(private val locationManager: LocationManager, private val apiKey: String) :
    ViewModel() {

    val city = MutableLiveData<String>()
    val temp = MutableLiveData<String>()
    val pressure = MutableLiveData<String>()
    val humidity = MutableLiveData<String>()
    val windDeg = MutableLiveData<String>()
    val windSpeed = MutableLiveData<String>()
    val iconUrl = MutableLiveData<String>()

    fun getCurrentWeatherByCity(_city: String) = viewModelScope.launch {
        val response = try {
            RetrofitInstance.api.getCurrentWeatherByCity(
                city = _city,
                units = "metric",
                apiKey = apiKey
            )
        } catch (e: IOException) {
            Log.d("EXC", "${e.message}")
            return@launch
        } catch (e: HttpException) {
            Log.d("EXC", "${e.message}")
            return@launch
        }
        val data = response.body()
        if (response.isSuccessful && data != null) {
            updateData(data)
        }
    }

    private fun getLocation(): Pair<Double, Double>? {
        var lat = 0.0
        var lon = 0.0
        try {
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                1000 * 10,
                500f
            )
            { location ->
                lat = location.latitude
                lon = location.longitude
            }
            return Pair(lat, lon)
        }
        catch (e: SecurityException) {
            return null
        }
    }

    fun getCurrentWeatherByLocation() = viewModelScope.launch {
        val location = getLocation() ?: return@launch
        val (lat, lon) = location
        val response = try {
            RetrofitInstance.api.getCurrentWeatherByLocation(
                lat = lat.toString(),
                lon = lon.toString(),
                units = "metric",
                apiKey = apiKey
            )
        } catch (e: IOException) {
            Log.d("EXC", "${e.message}")
            return@launch
        } catch (e: HttpException) {
            Log.d("EXC", "${e.message}")
            return@launch
        }
        val data = response.body()
        if (response.isSuccessful && data != null) {
            updateData(data)
        }

    }

    private fun updateData(data: CurrentWeather) {
        city.value = data.name
        temp.value = data.main.temp.toInt()
            .toString() + " (" + data.main.temp_min.toInt() + "..." + data.main.temp_max.toInt() + ")"
        pressure.value = (data.main.pressure / 1.33).toInt().toString()
        humidity.value = data.main.humidity.toString()
        windDeg.value = data.wind.deg.toString()
        windSpeed.value = data.wind.speed.toString()
        val iconId = data.weather[0].icon
        iconUrl.value = "https://openweathermap.org/img/wn/$iconId@2x.png"
    }
}