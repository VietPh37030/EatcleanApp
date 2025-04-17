package com.example.eatclean.ui.theme.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.toSize
import androidx.compose.material3.Text

@Composable
fun GaugeChart(
    modifier: Modifier = Modifier,
    percent: Float, // 0.0 - 1.0
    centerText: String,
    rightText: String,
    leftTopText: String,
    leftBottomText: String,
    rightTopText: String,
    rightBottomText: String,
    color: Color = MaterialTheme.colorScheme.primary,
    trackColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    stroke: Dp = 14.dp
) {
    val arcStroke = with(LocalDensity.current) { stroke.toPx() }
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        val sweep = 270f * percent
        Canvas(modifier = Modifier.fillMaxSize()) {
            val diameter = size.minDimension
            // Track
            drawArc(
                color = trackColor,
                startAngle = 135f,
                sweepAngle = 270f,
                useCenter = false,
                style = Stroke(width = arcStroke, cap = StrokeCap.Round)
            )
            // Progress
            drawArc(
                color = color,
                startAngle = 135f,
                sweepAngle = sweep,
                useCenter = false,
                style = Stroke(width = arcStroke, cap = StrokeCap.Round)
            )
        }
        // Center Text
        Text(
            text = centerText,
            style = TextStyle(
                color = color,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            ),
            modifier = Modifier.align(Alignment.Center)
        )
        // Right Text
        Text(
            text = rightText,
            style = TextStyle(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            ),
            modifier = Modifier.align(Alignment.CenterEnd)
        )
        // Left Top Text
        Text(
            text = leftTopText,
            style = TextStyle(
                color = Color.Yellow,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            ),
            modifier = Modifier.align(Alignment.TopStart)
        )
        // Left Bottom Text
        Text(
            text = leftBottomText,
            style = TextStyle(
                color = Color.Gray,
                fontSize = 12.sp
            ),
            modifier = Modifier.align(Alignment.BottomStart)
        )
        // Right Top Text
        Text(
            text = rightTopText,
            style = TextStyle(
                color = Color.Blue,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            ),
            modifier = Modifier.align(Alignment.TopEnd)
        )
        // Right Bottom Text
        Text(
            text = rightBottomText,
            style = TextStyle(
                color = Color.Gray,
                fontSize = 12.sp
            ),
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}
