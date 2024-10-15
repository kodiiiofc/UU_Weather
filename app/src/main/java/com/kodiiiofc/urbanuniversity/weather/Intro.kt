package com.kodiiiofc.urbanuniversity.weather

import java.io.Serializable

data class Intro(val text: String, val imageResource: Int, var isLastPage: Boolean = false) : Serializable