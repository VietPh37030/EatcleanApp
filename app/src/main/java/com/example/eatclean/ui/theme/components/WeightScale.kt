package com.example.eatclean.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun WeightScale(
    initialWeight: Float = 77f,
    minWeight: Int = 20,
    maxWeight: Int = 100,
    primaryColor: Color = Color(0xFF4CAF50),
    secondaryColor: Color = Color(0xFF2196F3),
    unit: String = "kg",
    onWeightChange: (Float) -> Unit
) {
    val ticksPerKg = 10 // Đơn vị chia nhỏ là 0.1kg
    val tickSpacingDp = 20.dp
    val totalTicks = (maxWeight - minWeight) * ticksPerKg + 1

    val density = LocalDensity.current
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val tickSpacingPx = with(density) { tickSpacingDp.toPx() }
    val centerOffsetPx = with(density) { screenWidth.toPx() / 2.1f }

    // Đảm bảo làm tròn ban đầu
    val roundedInitialWeight = (initialWeight * ticksPerKg).roundToInt() / ticksPerKg.toFloat()

    // Tính toán offset ban đầu sao cho đúng vạch tick
    val initialOffset =
        ((roundedInitialWeight - minWeight) * ticksPerKg * tickSpacingPx - centerOffsetPx + tickSpacingPx / 6).roundToInt()

    var currentWeight by remember { mutableStateOf(roundedInitialWeight) }

    val scrollState = rememberScrollState(initial = initialOffset)

    // Cập nhật trọng lượng từ offset cuộn, chỉ thay đổi khi đã vượt qua một vạch tick đầy đủ
    fun calculateWeightFromScroll(scroll: Int): Float {
        val raw =
            minWeight + (scroll + centerOffsetPx + tickSpacingPx / 6) / tickSpacingPx / ticksPerKg
        return (raw * ticksPerKg).toInt() / ticksPerKg.toFloat()
    }

//    // Cập nhật vạch chỉ khi có sự thay đổi đáng kể
//    LaunchedEffect(scrollState.value) {
//        val weight = calculateWeightFromScroll(scrollState.value)
//
//        // Nếu trọng lượng đã thay đổi và vượt qua ngưỡng, cập nhật
//        if (weight != currentWeight) {
//            currentWeight = weight.coerceIn(minWeight.toFloat(), maxWeight.toFloat())
//            onWeightChange(currentWeight)
//        }
//    }
    // Thêm biến để lưu giá trị đã báo cáo trước đó
    var lastReportedWeight by remember { mutableStateOf(roundedInitialWeight) }

    // Sửa LaunchedEffect để tránh cập nhật liên tục
    LaunchedEffect(scrollState.value) {
        val weight = calculateWeightFromScroll(scrollState.value)
        val roundedWeight = weight.coerceIn(minWeight.toFloat(), maxWeight.toFloat())

        // Chỉ gọi callback khi giá trị thực sự thay đổi
        if (roundedWeight != lastReportedWeight) {
            lastReportedWeight = roundedWeight
            currentWeight = roundedWeight
            onWeightChange(roundedWeight)
        }
    }

//    // MVVM: Cập nhật weight và thông báo cho ViewModel khi có thay đổi
//    LaunchedEffect(scrollState.value) {
//        val weight = calculateWeightFromScroll(scrollState.value)
//
//        // Nếu trọng lượng đã thay đổi và vượt qua ngưỡng, cập nhật
//        if (weight != currentWeight) {
//            currentWeight = weight.coerceIn(minWeight.toFloat(), maxWeight.toFloat())
//            onWeightChange(currentWeight)  // MVVM: Gọi callback để cập nhật ViewModel
//        }
//    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        Text(
            text = String.format("%.1f %s", currentWeight,unit),
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold,
            color = primaryColor,
            modifier = Modifier.padding(10.dp)
        )

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
        ) {
            Row(
                modifier = Modifier
                    .horizontalScroll(scrollState)
                    .width(tickSpacingDp * totalTicks)
                    .fillMaxHeight(),
                verticalAlignment = Alignment.Bottom
            ) {
                for (i in 0 until totalTicks) {
                    val tickWeight = minWeight + i.toFloat() / ticksPerKg
                    val isMajor = tickWeight % 1f == 0f
                    val isHalf = tickWeight % 0.5f == 0f
                    val selected = tickWeight == currentWeight

                    Column(
                        modifier = Modifier
                            .width(tickSpacingDp)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Bottom, // ✅ Căn đáy
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (isMajor && !selected) {
                            Text(
                                text = "${tickWeight.roundToInt()}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.DarkGray
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Box(
                            modifier = Modifier
                                .width(2.dp)
                                .height(
                                    when {
                                        isMajor -> 40.dp
                                        isHalf -> 30.dp
                                        else -> 20.dp
                                    }
                                )
                                .background(Color.Black)
                        )
                    }
                }
            }

            // Vạch chỉ màu
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .width(4.dp)
                    .fillMaxHeight()
                    .background(secondaryColor)
            )
        }
    }
}