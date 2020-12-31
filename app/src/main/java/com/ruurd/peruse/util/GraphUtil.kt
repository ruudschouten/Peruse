package com.ruurd.peruse.util

import android.content.Context
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.ruurd.peruse.R
import com.ruurd.peruse.models.Book

object GraphUtil {
    /**
     * Create and return a new LineDataSet and apply styling to it.
     */
    fun createStyledLineDataSet(
        context: Context,
        entries: ArrayList<Entry>,
        label: String,
        color: Int = R.color.colorPrimary,
        darkerColor: Int = R.color.colorPrimaryDark
    ): LineDataSet {
        val dataSet = LineDataSet(entries, label)
        dataSet.lineWidth = 5f
        dataSet.color = ContextCompat.getColor(context, color)
        dataSet.fillAlpha = 255
        dataSet.setCircleColor(ContextCompat.getColor(context, darkerColor))
        dataSet.setDrawCircleHole(true)
        dataSet.circleHoleColor = ContextCompat.getColor(context, darkerColor)
        dataSet.setDrawHighlightIndicators(false)
        return dataSet
    }

    /**
     * Style the line chart and disable some features on it.
     */
    fun styleLineChart(chart: LineChart, context: Context) {
        // Hide the axis information on the right side.
        chart.axisRight.isEnabled = false

        // Set all text colors
        styleFontColors(chart, context)

        // Put the X axis on the bottom of the graph, instead of above it.
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        // Limit this axis to 5 labels.
        chart.xAxis.setLabelCount(5, true)
        // Hide the description view.
        chart.description = null
        // Hide the legend.
        chart.legend.isEnabled = false
        // Disable double tapping to zoom in, since the user might accidentally do this.
        chart.isDoubleTapToZoomEnabled = false
    }

    fun styleFontColors(chart: LineChart, context: Context, color: Int = R.color.white) {
        chart.axisLeft.textColor = ContextCompat.getColor(context, color)
        chart.xAxis.textColor = ContextCompat.getColor(context, color)
        chart.legend.textColor = ContextCompat.getColor(context, color)
    }

    fun bookToEntries(book: Book): ArrayList<Entry> {
        val entries = mutableListOf<Entry>()
        book.chapters.forEach {
            entries.add(Entry(it.date.toFloat(), it.end.toFloat()))
        }

        return entries as ArrayList<Entry>
    }
}