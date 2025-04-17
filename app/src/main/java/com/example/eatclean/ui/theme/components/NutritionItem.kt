package com.example.eatclean.ui.theme.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NutritionItem(
    iconRes: Int, // ID của icon
    name: String, // Tên mục (Cholesterol, Omega-3, v.v.)
    currentValue: Int, // Giá trị hiện tại
    targetValue: Int, // Giá trị mục tiêu
    unit: String, // Đơn vị (mg, g, v.v.)
    progressColor: Color, // Màu của thanh tiến trình
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = "Icon $name",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))

            // Tên và giá trị còn lại
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Còn lại ${targetValue - currentValue}$unit",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            // Giá trị hiện tại/mục tiêu và phần trăm
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "$currentValue / ${targetValue}$unit",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                val percentage = if (targetValue > 0) (currentValue * 100 / targetValue) else 0
                Text(
                    text = "$percentage%",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }

        // Thanh tiến trình
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { if (targetValue > 0) (currentValue.toFloat() / targetValue.toFloat()).coerceIn(0f, 1f) else 0f },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = progressColor, // Màu của thanh tiến trình
            trackColor = Color.LightGray // Sửa backgroundColor thành trackColor
        )
    }
}