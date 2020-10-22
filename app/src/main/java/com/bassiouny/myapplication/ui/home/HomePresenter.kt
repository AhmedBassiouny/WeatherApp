package com.bassiouny.myapplication.ui.home

import android.graphics.Color
import com.bassiouny.myapplication.R
import com.bassiouny.myapplication.model.BaseData
import com.bassiouny.myapplication.model.Entries
import com.bassiouny.myapplication.network.Api
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import java.util.*
import kotlin.collections.ArrayList

class HomePresenter(
    private val view: HomeView,
    private val mainThread: Scheduler,
    private val io: Scheduler,
    private val service: Api
) {
    private val weatherInteractor = WeatherInteractor()
    private var subscription: Disposable? = null
    private lateinit var entries: List<Entries>


    fun onViewCreated() {
        if (subscription == null) {
            view.showLoading()
            subscription = weatherInteractor.getWeatherData(mainThread, io, service)
                .subscribe({
                    val entries = it.data.me.home.weather.entries
                    if (entries.isEmpty()) {
                        view.noResults()
                    }
                    view.hideLoading()
                    subscription = null
                    prepareData(it)
                },
                    {
                        view.showError(it.message.toString())
                        view.hideLoading()
                        subscription = null
                    })
        }
    }

    fun prepareData(baseData: BaseData) {
        val weather = baseData.data.me.home.weather
        entries = weather.entries
        val weatherData: MutableList<Entry> = ArrayList()
        for (entry in entries) {
            val time = entry.time.split('T')[1].subSequence(0, 2).toString().toFloat()
            weatherData.add(Entry(time, entry.temperature.toFloat()))
        }

        val dataSet = LineDataSet(weatherData, "")
        dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        dataSet.cubicIntensity = 0.2f
        dataSet.setDrawFilled(false)
        dataSet.setDrawCircles(true)
        dataSet.lineWidth = 1.8f
        dataSet.color = Color.WHITE
        dataSet.fillAlpha = 100

        dataSet.setDrawValues(false)
        dataSet.setDrawHorizontalHighlightIndicator(false)

        view.showDataAndAnimate(LineData(dataSet), weather.maxTemperature, weather.minTemperature)

        getTempAndBackgroundTimeBased(currentTime())
    }

     fun currentTime(): Int = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

    fun getTempAndBackground(currentTime: Int) {
        val entry = entries[currentTime]
        // should be different images, one for each weather
        val bgId = when (entry.type) {
            "rain" -> R.drawable.night
            "cloud" -> R.drawable.sunnybg
            "snow" -> R.drawable.night
            "sun" -> R.drawable.sunnybg
            else -> R.drawable.night
        }
        view.changeTempAndBg(Pair(entry.temperature, bgId))
    }

    fun getTempAndBackgroundTimeBased(currentTime: Int) {
        val entry = entries[currentTime]
        // should be different images, one for each weather
        val bgId = when (currentTime) {
            in 7..18 -> R.drawable.sunnybg
            else -> R.drawable.night
        }
        view.changeTempAndBg(Pair(entry.temperature, bgId))
    }


}