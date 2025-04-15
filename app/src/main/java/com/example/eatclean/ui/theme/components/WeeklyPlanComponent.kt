package com.example.eatclean.ui.theme.components



import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun WeeklyPlanComponent(
    weeklyPlan: List<WeeklyMealPlan>,
    onDayClick: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = "Kế hoạch dinh dưỡng hàng tuần",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn {
            items(weeklyPlan) { dayPlan ->
                WeeklyDayCard(
                    dayPlan = dayPlan,
                    onClick = { onDayClick(dayPlan.dayName) }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun WeeklyDayCard(
    dayPlan: WeeklyMealPlan,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F4F6))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Day header with total calories
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = dayPlan.dayName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFFFEBEB))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "${dayPlan.totalCalories} kcal",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF6B6B)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Nutrition summary
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                NutrientBadge(
                    name = "Protein",
                    value = "${dayPlan.totalProtein}g",
                    color = Color(0xFF4FC3F7)
                )

                NutrientBadge(
                    name = "Carbs",
                    value = "${dayPlan.totalCarbs}g",
                    color = Color(0xFFFF8A65)
                )

                NutrientBadge(
                    name = "Fat",
                    value = "${dayPlan.totalFat}g",
                    color = Color(0xFFFFFD54)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Meal summary
            dayPlan.mealNames.take(3).forEach { mealName ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "• $mealName",
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            if (dayPlan.mealNames.size > 3) {
                Text(
                    text = "+ ${dayPlan.mealNames.size - 3} món khác",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // View details button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Xem chi tiết",
                    fontSize = 14.sp,
                    color = Color(0xFF4FC3F7),
                    fontWeight = FontWeight.Bold
                )

                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "View details",
                    tint = Color(0xFF4FC3F7),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
fun NutrientBadge(
    name: String,
    value: String,
    color: Color
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = "$name: $value",
            fontSize = 12.sp,
            color = Color.White,
            fontWeight = FontWeight.Medium
        )
    }
}

// Model class for weekly plan data
data class WeeklyMealPlan(
    val dayName: String,
    val totalCalories: Int,
    val totalProtein: Int,
    val totalCarbs: Int,
    val totalFat: Int,
    val mealNames: List<String>
)