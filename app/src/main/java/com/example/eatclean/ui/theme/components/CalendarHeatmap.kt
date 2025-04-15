package com.example.eatclean.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate

@Composable
fun CalendarHeatmap(data: Map<LocalDate, Int>, modifier: Modifier = Modifier) {
    val today = LocalDate.now()
    val firstDayOfMonth = today.withDayOfMonth(1)
    val lastDayOfMonth = today.withDayOfMonth(today.lengthOfMonth())

    val days = (0 until today.lengthOfMonth()).map {
        firstDayOfMonth.plusDays(it.toLong())
    }

    val maxCount = (data.values.maxOrNull() ?: 1).coerceAtLeast(1)

    Column(modifier = modifier) {
        Text("Báo cáo lịch tháng", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        val weekdays = listOf("T2", "T3", "T4", "T5", "T6", "T7", "CN")

        // Calculate offset - In Java LocalDate: Monday = 1, Tuesday = 2, etc.
        // Since our weekdays list starts with Monday (index 0), we need to calculate how many
        // empty cells to add at the beginning
        val firstDayOfWeekValue = firstDayOfMonth.dayOfWeek.value // Monday = 1, Tuesday = 2, etc.
        val offset = firstDayOfWeekValue - 1 // For Monday, offset = 0; For Tuesday, offset = 1, etc.

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            weekdays.forEach { day ->
               Box(
                   modifier = Modifier.weight(1f),
                   contentAlignment = Alignment.Center
               ){
                   Text(
                       text = day,
                       fontSize = 12.sp,
                       fontWeight = FontWeight.Medium
                   )
               }
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.height(200.dp)
        ) {
            // First add empty spaces for days before the month starts
            items(offset) {
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .size(30.dp)
                )
            }

            // Then add all days of the month
            items(days.size) { index ->
                val date = days[index]
                val value = data[date] ?: 0
                val backgroundColor = getColorForValue(value, maxCount)

                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .size(30.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(backgroundColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = date.dayOfMonth.toString(),
                        fontSize = 12.sp,
                        color = if (value > maxCount * 0.5f) Color.White else Color.Black
                    )
                }
            }
        }
    }
}


fun getColorForValue(value: Int, max: Int): Color {
    return when {
        value == 0 -> Color(0xFFE0E0E0) // xám nhạt
        value <= max * 0.25 -> Color(0xFF81D4FA) // xanh nhạt
        value <= max * 0.5 -> Color(0xFFFFF176) // vàng
        value <= max * 0.75 -> Color(0xFFFFB74D) // cam
        else -> Color(0xFFE57373) // đỏ
    }
}
