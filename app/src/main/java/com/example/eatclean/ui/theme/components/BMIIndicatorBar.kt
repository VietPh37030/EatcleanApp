package com.example.eatclean.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BMIIndicatorBar(
    bmi: Float,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(60.dp)
        .padding(10.dp)
) {
    val colors = listOf(
        Color(0xFF42A5F5), // Gầy
        Color(0xFF66BB6A), // Bình thường
        Color(0xFFFFA726), // Thừa cân
        Color(0xFFEF5350)  // Béo phì
    )
    val labels = listOf("Gầy", "Bình thường", "Thừa cân", "Béo phì")

    BoxWithConstraints(modifier = modifier) {
        val fullWidthPx = constraints.maxWidth.toFloat()

        // Tính vị trí vạch chỉ BMI theo UI chia đều
        val uiRegions = listOf(0f to 18.5f, 18.5f to 25f, 25f to 30f, 30f to 40f)
        val regionWidthFraction = 1f / uiRegions.size

        val (regionIndex, regionStart, regionEnd) = uiRegions.withIndex().firstOrNull { (_, range) ->
            bmi in range.first..range.second
        }?.let { (i, range) ->
            Triple(i, range.first, range.second)
        } ?: Triple(0, 0f, 18.5f)

        val bmiInRegionFraction = ((bmi - regionStart) / (regionEnd - regionStart)).coerceIn(0f, 1f)
        val bmiUIPositionFraction = regionIndex * regionWidthFraction + bmiInRegionFraction * regionWidthFraction
        val bmiPositionPx = fullWidthPx * bmiUIPositionFraction

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // --- Mốc BMI trên giao diện (chia đều theo UI) ---
            Box(modifier = Modifier.fillMaxWidth()) {
                val displayBMIs = listOf("18.5", "25.0", "30.0")
                val positions = listOf(0.25f, 0.5f, 0.75f) // UI offset chia đều

                positions.forEachIndexed { index, pos ->
                    val offset = (fullWidthPx * pos).toInt()
                    Text(
                        text = displayBMIs[index],
                        fontSize = 10.sp,
                        color = Color.Gray,
                        modifier = Modifier
                            .offset { IntOffset(offset - 10.dp.toPx().toInt(), 0) }
                    )
                }
            }


            // --- Thanh màu và vạch ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .clip(RoundedCornerShape(6.dp))
            ) {
                Row(Modifier.fillMaxSize()) {
                    colors.forEach { color ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .background(color)
                        )
                    }
                }

                // --- Vạch chỉ BMI ---
                Box(
                    modifier = Modifier
                        .offset { IntOffset(x = (bmiPositionPx - 1.dp.toPx()).toInt().coerceAtMost((fullWidthPx - 2.dp.toPx()).toInt()), y = 0) }
                        .width(2.dp)
                        .fillMaxHeight()
                        .background(Color.Black)
                )
            }

            // --- Label bên dưới ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                labels.forEach { label ->
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        Text(
                            text = label,
                            fontSize = 10.sp,
                            color = Color.DarkGray
                        )
                    }
                }
            }
        }
    }
}