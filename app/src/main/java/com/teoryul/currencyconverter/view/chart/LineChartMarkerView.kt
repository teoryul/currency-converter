package com.teoryul.currencyconverter.view.chart

import android.content.Context
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import kotlinx.android.synthetic.main.line_chart_marker_view.view.*

class LineChartMarkerView(context: Context, layoutResource: Int, private val xAxisMappings: HashMap<Float, String>) :
    MarkerView(context, layoutResource) {

    private val offset = MPPointF()

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        txtDateValue.text = xAxisMappings[e?.x] ?: ""
        txtRateValue.text = e?.y.toString()
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF =
        if (offset.x == 0f && offset.y == 0f) {
            offset.x = -(width * 0.5).toFloat()
            offset.y = -height.toFloat()
            offset
        } else {
            offset
        }
}