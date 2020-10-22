package com.bassiouny.myapplication.ui.home

import com.bassiouny.myapplication.model.BaseData
import com.bassiouny.myapplication.model.QueryBody
import com.bassiouny.myapplication.network.Api

import io.reactivex.Observable
import io.reactivex.Scheduler

class WeatherInteractor {
    fun getWeatherData(
        mainThread: Scheduler,
        io: Scheduler,
        api: Api
    ): Observable<BaseData> {

        return api.fetchWeatherForecast(QueryBody().sd)
            .observeOn(mainThread)
            .subscribeOn(io)
    }
}