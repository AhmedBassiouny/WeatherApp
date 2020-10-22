package com.bassiouny.myapplication.model

import com.google.gson.annotations.SerializedName

data class Me(

	@SerializedName("home") val home: Home
)