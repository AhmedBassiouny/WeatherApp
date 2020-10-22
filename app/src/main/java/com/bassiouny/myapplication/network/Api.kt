package com.bassiouny.myapplication.network

import com.bassiouny.myapplication.model.BaseData
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Api {

    @POST("gql/")
    @FormUrlEncoded
    fun fetchWeatherForecast(@Field("query") query: String): Observable<BaseData>
}