// File: com/example/eatclean/ui/theme/components/WeeklyDayCard.kt
package com.example.eatclean.ui.theme.components

import MealSummary
import WeeklyMealPlan
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eatclean.R


@Composable
fun WeeklyDayCard(
    dayPlan: WeeklyMealPlan,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (dayPlan.isVip) Color(0xFFE0F7FA) else Color(0xFFF5F5F5)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Tiêu đề: Ngày và tổng calo
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = dayPlan.dayName,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    ),
                    color = Color.Black
                )
                Text(
                    text = "${dayPlan.totalCalories} kcal",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    ),
                    color = Color(0xFFFF6B6B) // Màu đỏ nhạt cho calo
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Danh sách bữa ăn
            dayPlan.meals.forEach { meal ->
                MealRow(meal = meal)
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Nút "Thay thế" hoặc "Lưu và thay phần"
            Button(
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (dayPlan.isVip) Color(0xFF0288D1) else Color(0xFF757575),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = if (dayPlan.isVip) "Thay thế" else "Lưu và thay phần",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun MealRow(meal: MealSummary) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Biểu tượng cho bữa ăn
        val icon = when (meal.mealType) {
            "Bữa sáng" -> painterResource(id = R.drawable.fat)
            "Bữa trưa" -> painterResource(id = R.drawable.fat)
            "Bữa tối" -> painterResource(id = R.drawable.fat)
            "Ăn vặt" -> painterResource(id = R.drawable.fat)
            else -> painterResource(id = R.drawable.heart) // bạn có thể thêm 1 ảnh mặc định
        }


        Image(
            painter = icon,
            contentDescription = meal.mealType,
            modifier = Modifier.size(24.dp)
        )


        Spacer(modifier = Modifier.width(12.dp))

        // Thông tin bữa ăn
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = meal.mealType,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                ),
                color = Color.Black
            )
            Text(
                text = meal.description,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                color = Color.Gray
            )
        }

        // Calo của bữa ăn
        Text(
            text = "${meal.calories} kcal",
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp
            ),
            color = Color.Black
        )
    }
}