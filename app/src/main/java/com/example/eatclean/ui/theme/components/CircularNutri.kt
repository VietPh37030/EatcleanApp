package com.example.eatclean.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.min

@Composable
fun CircularNutri(
    currentValue: Int,
    targetValue: Int,
    label: String,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val progress = if (targetValue > 0) {
        (currentValue.toFloat() / targetValue.toFloat()).coerceIn(0f, 1f)
    } else {
        0f
    }
    val sweepAngle = 360f * progress

    Column(
        modifier = modifier.clickable(onClick=onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(160.dp)
                .background(color = Color.LightGray.copy(alpha = 0.2f))
                .clip(RoundedCornerShape(16.dp))
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.size(120.dp)) {
                val strokeWidth = 10.dp.toPx()
                val diameter = min(size.width, size.height) - strokeWidth
                val topLeft = androidx.compose.ui.geometry.Offset(
                    x = (size.width - diameter) / 2,
                    y = (size.height - diameter) / 2
                )

                drawArc(
                    color = Color.LightGray,
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
                    topLeft = topLeft,
                    size = androidx.compose.ui.geometry.Size(diameter, diameter),
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )

                if (progress > 0) {
                    drawArc(
                        color = color,
                        startAngle = -90f,
                        sweepAngle = sweepAngle,
                        useCenter = false,
                        topLeft = topLeft,
                        size = androidx.compose.ui.geometry.Size(diameter, diameter),
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = currentValue.toString(),
                    color = color,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "/${targetValue}kcal",
                    color = color.copy(alpha = 0.8f),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                color = color,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Còn lại ${targetValue - currentValue}kcal",
                fontSize = 11.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}