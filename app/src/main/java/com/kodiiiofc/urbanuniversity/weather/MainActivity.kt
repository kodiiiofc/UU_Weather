package com.kodiiiofc.urbanuniversity.weather

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var cityTV: TextView
    private lateinit var tempTV: TextView
    private lateinit var pressureTV: TextView
    private lateinit var humidityTV: TextView
    private lateinit var windDegTV: TextView
    private lateinit var windSpeedTV: TextView
    private lateinit var iconIV: ImageView
    private lateinit var cityET: EditText
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cityTV = findViewById(R.id.tv_city)
        tempTV = findViewById(R.id.tv_temp)
        pressureTV = findViewById(R.id.tv_pressure)
        humidityTV = findViewById(R.id.tv_humidity)
        windDegTV = findViewById(R.id.tv_wind_deg)
        windSpeedTV = findViewById(R.id.tv_wind_speed)
        iconIV = findViewById(R.id.iv_icon)
        cityET = findViewById(R.id.et_city)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        mainViewModel = ViewModelProvider(this, MainViewModelFactory(this))
            .get(MainViewModel::class.java)

        observeMainViewModel()
        mainViewModel.getCurrentWeatherByLocation()

        findViewById<Button>(R.id.btn_update).setOnClickListener {
            mainViewModel.getCurrentWeatherByCity(cityET.text.toString().trim())
        }
    }

    private fun observeMainViewModel() {
        mainViewModel.city.observe(this) {
            cityTV.text = it
        }
        mainViewModel.temp.observe(this) {
            tempTV.text = "$it ${getString(R.string.units_deg_c)}"
        }
        mainViewModel.pressure.observe(this) {
            pressureTV.text = "$it ${getString(R.string.units_pressure)}"
        }
        mainViewModel.humidity.observe(this) {
            humidityTV.text = "${getString(R.string.tv_humidity)} $it %"
        }
        mainViewModel.windDeg.observe(this) {
            windDegTV.text = "${getString(R.string.tv_wind_deg)} $it"
        }
        mainViewModel.windSpeed.observe(this) {
            windSpeedTV.text = "$it ${getString(R.string.units_wind_speed)}"
        }
        mainViewModel.iconUrl.observe(this) {
            Picasso.get().load(it).into(iconIV)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menu?.add("Выйти")!!.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.title) {
            "Выйти" -> finish()
        }

        return super.onOptionsItemSelected(item)
    }
}