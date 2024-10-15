package com.kodiiiofc.urbanuniversity.weather

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModelFactory(context: Context) : ViewModelProvider.Factory {

    private val apiKey = context.getString(R.string.api)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(apiKey = apiKey) as T
    }

}