package com.kodiiiofc.urbanuniversity.weather

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2

class IntroActivity : AppCompatActivity() {

    private val introList : List<Intro> = listOf(
        Intro("Привествтуем!", R.drawable.bg_1),
        Intro("Отличная погода!", R.drawable.bg_2),
        Intro("Можете проверить дальше!", R.drawable.bg_3, true),
    )

    private lateinit var introVP: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_intro)
        introVP = findViewById(R.id.vp_intro)
        val adapter = ViewPagerIntroAdapter(this, introList)
        introVP.adapter = adapter
    }
}