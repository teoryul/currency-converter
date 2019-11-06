package com.teoryul.currencyconverter.utils

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.teoryul.currencyconverter.R
import com.teoryul.currencyconverter.persistence.model.ConversionRatePersist
import com.teoryul.currencyconverter.ui.extensions.round
import com.teoryul.currencyconverter.utils.DateUtils.getDayAndMonthFromLongTime
import com.teoryul.currencyconverter.view.chart.LineChartMarkerView

fun getLineDataSetValues(chartData: List<ConversionRatePersist>): Pair<List<Entry>, HashMap<Float, String>> {
    val dataSetEntries = mutableListOf<Entry>()
    val xAxisMappings = hashMapOf<Float, String>()

    for (i in 0 until chartData.size) {
        dataSetEntries.add(Entry(i.toFloat(), chartData[i].rate.round(6)))
        xAxisMappings[i.toFloat()] = getDayAndMonthFromLongTime(chartData[i].date)
    }

    return Pair(dataSetEntries, xAxisMappings)
}

fun createLineDataSet(context: Context, values: List<Entry>, label: String = ""): LineDataSet {
    val resources = context.resources

    val dataSet = LineDataSet(values, label)
    dataSet.setDrawValues(false)
    dataSet.setDrawCircles(false)

    dataSet.lineWidth = resources.getDimension(R.dimen.chart_data_set_line_width)
    dataSet.color = ContextCompat.getColor(context, R.color.colorWhite)
    dataSet.setDrawFilled(true)
    dataSet.fillColor = ContextCompat.getColor(context, R.color.colorWhite)

    val fillAlphaValue = TypedValue()
    resources.getValue(R.dimen.chart_data_set_fill_alpha, fillAlphaValue, true)
    dataSet.fillAlpha = fillAlphaValue.float.toInt()

    dataSet.setDrawHorizontalHighlightIndicator(false)
    dataSet.highLightColor = ContextCompat.getColor(context, R.color.colorWhite)
    dataSet.highlightLineWidth = resources.getDimension(R.dimen.chart_data_set_highlight_line_width)

    return dataSet
}

fun configureLineChart(context: Context, chart: LineChart, xAxisMappings: HashMap<Float, String>) {
    val resources = context.resources

    chart.marker = LineChartMarkerView(context, R.layout.line_chart_marker_view, xAxisMappings)

    chart.legend.isEnabled = false
    chart.description.isEnabled = false
    chart.setScaleEnabled(false)
    chart.setDrawBorders(false)
    chart.isHighlightPerTapEnabled = true
    chart.isHighlightPerDragEnabled = true
    chart.extraRightOffset = resources.getDimension(R.dimen.chart_extra_right_offset)
    chart.extraBottomOffset = resources.getDimension(R.dimen.chart_extra_bottom_offset)

    chart.xAxis.setDrawGridLines(false)
    chart.axisRight.setDrawLabels(false)
    chart.axisRight.setDrawGridLines(false)

    chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
    chart.xAxis.yOffset = resources.getDimension(R.dimen.chart_x_axis_y_offset)
    chart.axisLeft.xOffset = resources.getDimension(R.dimen.chart_axis_left_x_offset)

    chart.xAxis.textSize = resources.getDimension(R.dimen.chart_axis_txt_size)
    chart.axisLeft.textSize = resources.getDimension(R.dimen.chart_axis_txt_size)

    chart.xAxis.labelRotationAngle = resources.getDimension(R.dimen.chart_x_axis_label_rotation_angle)

    chart.xAxis.textColor = ContextCompat.getColor(context, R.color.colorWhite)
    chart.axisLeft.textColor = ContextCompat.getColor(context, R.color.colorWhite)
    chart.axisLeft.gridColor = ContextCompat.getColor(context, R.color.colorWhite)

    chart.xAxis.valueFormatter = object : ValueFormatter() {
        override fun getFormattedValue(value: Float): String = xAxisMappings[value] ?: ""
    }
}