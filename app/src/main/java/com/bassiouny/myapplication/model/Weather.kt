package com.bassiouny.myapplication.model

import com.google.gson.annotations.SerializedName

data class Weather(

	@SerializedName("minTemperature") val minTemperature: Double,
	@SerializedName("maxTemperature") val maxTemperature: Double,
	@SerializedName("entries") val entries: List<Entries>
)