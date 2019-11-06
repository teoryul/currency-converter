package com.teoryul.currencyconverter.binding

import android.databinding.BindingAdapter
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.LineData
import com.teoryul.currencyconverter.App
import com.teoryul.currencyconverter.persistence.model.ConversionRatePersist
import com.teoryul.currencyconverter.utils.configureLineChart
import com.teoryul.currencyconverter.utils.createLineDataSet
import com.teoryul.currencyconverter.utils.getLineDataSetValues

@BindingAdapter("chartData")
fun setChartData(chart: LineChart, chartData: List<ConversionRatePersist>) {
    if (chartData.isNotEmpty()) {
        val context = App.instance.applicationContext
        val dataSetValues = getLineDataSetValues(chartData)
        configureLineChart(context, chart, dataSetValues.second)
        chart.data = LineData(listOf(createLineDataSet(context, dataSetValues.first)))
        chart.animateX(250)
    }
}