package com.bassiouny.myapplication.ui.home

import com.github.mikephil.charting.data.LineData

interface HomeView {
    fun showLoading()
    fun noResults()
    fun hideLoading()
    fun showError(s: String)
    fun changeTempAndBg(pair: Pair<Double, Int>)
    fun showDataAndAnimate(lineData: LineData, maxTemperature: Double, minTemperature: Double)
}