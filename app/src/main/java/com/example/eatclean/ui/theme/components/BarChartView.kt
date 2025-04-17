package com.example.eatclean.ui.theme.components

import android.content.Context
import android.graphics.Color as GColor
import android.graphics.Typeface
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

import com.github.mikephil.charting.formatter.ValueFormatter

@Composable
fun BarChartView(
    modifier: Modifier = Modifier,
    values: List<Float>,
    labels: List<String>,
    avg: Float? = null
) {
    AndroidView(
        modifier = modifier,
        factory = { context: Context ->
            BarChart(context).apply {
                layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                setDrawGridBackground(false)
                setDrawBarShadow(false)
                setDrawValueAboveBar(true)
                setPinchZoom(false)
                setScaleEnabled(false)
                setTouchEnabled(false)
                axisRight.isEnabled = false

                xAxis.apply {
                    setDrawGridLines(false)
                    granularity = 1f
                    position = com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM
                    valueFormatter = object : ValueFormatter() {
                        override fun getFormattedValue(value: Float): String {
                            return labels.getOrNull(value.toInt()) ?: ""
                        }
                    }
                    textColor = GColor.DKGRAY
                    textSize = 12f
                }
                axisLeft.apply {
                    setDrawGridLines(true)
                    gridColor = GColor.LTGRAY
                    textColor = GColor.DKGRAY
                    textSize = 12f
                }
                legend.isEnabled = false
                description = Description().apply { text = "" }

                if (avg != null) {
                    val limitLine = LimitLine(avg, "avg")
                    limitLine.lineColor = GColor.parseColor("#4285F4")
                    limitLine.lineWidth = 2f
                    limitLine.textColor = GColor.parseColor("#4285F4")
                    limitLine.textSize = 12f
                    axisLeft.removeAllLimitLines()
                    axisLeft.addLimitLine(limitLine)
                }
            }
        },
        update = { chart ->
            val entries = values.mapIndexed { idx, v -> BarEntry(idx.toFloat(), v) }
            val dataSet = BarDataSet(entries, "").apply {
                color = GColor.parseColor("#4285F4")
                valueTextColor = GColor.DKGRAY
                valueTextSize = 12f
                valueTypeface = Typeface.DEFAULT_BOLD
            }
            chart.data = BarData(dataSet)
            chart.invalidate()
        }
    )
}
