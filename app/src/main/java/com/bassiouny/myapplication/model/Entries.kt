package com.bassiouny.myapplication.model

import com.google.gson.annotations.SerializedName

data class Entries (

	@SerializedName("time") val time : String,
	@SerializedName("temperature") val temperature : Double,
	@SerializedName("type") val type : String
)