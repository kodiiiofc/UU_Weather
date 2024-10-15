package com.kodiiiofc.urbanuniversity.weather

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodiiiofc.urbanuniversity.weather.utils.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MainViewModel(private val apiKey: String) : ViewModel() {

    val city = MutableLiveData<String>()
    val temp = MutableLiveData<String>()
    val pressure = MutableLiveData<String>()
    val humidity = MutableLiveData<String>()
    val windDeg = MutableLiveData<String>()
    val windSpeed = MutableLiveData<String>()
    val iconUrl = MutableLiveData<String>()

    fun getCurrentWeather(_city: String) = viewModelScope.launch {
        val response = try {
            RetrofitInstance.api.getCurrentWeather(
                city = _city,
                "metric",
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
            city.value = data.name
            temp.value = data.main.temp.toInt().toString() + " (" + data.main.temp_min.toInt() + "..." + data.main.temp_max.toInt() + ")"
            pressure.value = (data.main.pressure / 1.33).toInt().toString()
            humidity.value = data.main.humidity.toString()
            windDeg.value = data.wind.deg.toString()
            windSpeed.value = data.wind.speed.toString()
            val iconId = data.weather[0].icon
            iconUrl.value = "https://openweathermap.org/img/wn/$iconId@2x.png"
        }
    }

}