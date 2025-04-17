package com.example.eatclean.ui.theme.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp

@Composable
fun BarChart(
    values: List<Float>,
    labels: List<String>,
    target: Float,
    modifier: Modifier = Modifier,
    barColor: Color = Color(0xFF2196F3),
    targetLineColor: Color = Color.Red,
    averageLineColor: Color = Color.Blue
) {
    Box(
        modifier = modifier
            .height(180.dp)
            .fillMaxWidth()
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val maxVal = (values.maxOrNull() ?: 0f).coerceAtLeast(target).coerceAtLeast(200f)
            val barWidth = size.width / (values.size * 2)
            val space = barWidth

            // Draw bars
            values.forEachIndexed { i, v ->
                val barHeight = (v / maxVal) * size.height
                drawRoundRect(
                    color = barColor,
                    topLeft = Offset(x = i * (barWidth + space) + space / 2, y = size.height - barHeight),
                    size = androidx.compose.ui.geometry.Size(barWidth, barHeight),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(8f, 8f)
                )
            }

            // Draw target line
            val targetY = size.height - (target / maxVal) * size.height
            drawLine(
                color = targetLineColor,
                start = Offset(0f, targetY),
                end = Offset(size.width, targetY),
                strokeWidth = 4.dp.toPx(),
                cap = StrokeCap.Round,
                pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            )

            // Draw average line
            val avg = values.average().toFloat()
            val avgY = size.height - (avg / maxVal) * size.height
            drawLine(
                color = averageLineColor,
                start = Offset(0f, avgY),
                end = Offset(size.width, avgY),
                strokeWidth = 2.dp.toPx(),
                cap = StrokeCap.Round,
                pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            )
        }

        // X labels
        Row(
            Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            labels.forEach {
                androidx.compose.material3.Text(it, fontSize = 12.sp)
            }
        }
    }
}