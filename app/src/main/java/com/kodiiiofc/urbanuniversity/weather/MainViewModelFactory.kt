package com.kodiiiofc.urbanuniversity.weather

import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Process
import androidx.appcompat.app.AppCompatActivity.LOCATION_SERVICE
import androidx.core.content.PermissionChecker
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.security.Permission

class MainViewModelFactory(context: Context) : ViewModelProvider.Factory {

    private val apiKey = context.getString(R.string.api)

    private val locationManager: LocationManager =
        context.getSystemService(LOCATION_SERVICE) as LocationManager

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(locationManager = locationManager, apiKey = apiKey) as T
    }

}