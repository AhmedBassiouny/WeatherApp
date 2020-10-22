package com.bassiouny.myapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bassiouny.myapplication.R
import com.bassiouny.myapplication.network.Client
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(), HomeView {
    private var client = Client()
    private val presenter = HomePresenter(
        this, AndroidSchedulers.mainThread(),
        Schedulers.io(),
        client.getService()
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onStart() {
        super.onStart()
        presenter.onViewCreated()
    }

    private fun showData(lineData: LineData) {
        chart.data = lineData
        chart.axisLeft.setDrawAxisLine(false)
        chart.axisLeft.setDrawGridLines(false)
        chart.axisRight.setDrawAxisLine(false)
        chart.axisRight.setDrawGridLines(false)
        chart.axisRight.setDrawLabels(false)
        chart.xAxis.position = XAxis.XAxisPosition.TOP
        chart.xAxis.setDrawAxisLine(false)
        chart.xAxis.setDrawGridLines(false)
        chart.legend.isEnabled = false
        chart.isHighlightPerTapEnabled = true
        chart.highlightValue(presenter.currentTime().toFloat(), 0, false)

        chart.animateY(2000, Easing.EaseInOutBounce)
        chart.setScaleEnabled(false)

        chart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry, h: Highlight) {
                presenter.getTempAndBackgroundTimeBased(e.x.toInt())
            }

            override fun onNothingSelected() {}
        })
    }

    override fun showDataAndAnimate(
        lineData: LineData,
        maxTemperature: Double,
        minTemperature: Double
    ) {
        setMaxAndMin(maxTemperature, minTemperature)

        if (minTemperature > 0)
            chart.getAxis(AxisDependency.LEFT).axisMinimum = 0f
        showData(lineData)
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun noResults() {

    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    override fun showError(s: String) {
        Toast.makeText(requireContext(), "", Toast.LENGTH_LONG).show()
    }

    override fun changeTempAndBg(pair: Pair<Double, Int>) {
        currentTempTxt.text = pair.first.toString()
        mainLayout.background = ContextCompat.getDrawable(requireContext(), pair.second)
    }

    private fun setMaxAndMin(maxTemperature: Double, minTemperature: Double) {
        maxAndMin.text = "$maxTemperature/$minTemperature"
    }
}