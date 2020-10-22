package com.bassiouny.myapplication.model

import com.google.gson.annotations.SerializedName


data class Home (

	@SerializedName("weather") val weather : Weather
)