package com.example.eatclean.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eatclean.R
import com.example.eatclean.models.Nutrition

@Composable
fun NutritionSummary(
    nutritionData: List<Nutrition>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Kế hoạch ăn uống giảm cân cho nam",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            nutritionData.forEachIndexed { index, nutrition ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // Chọn icon dựa vào index hoặc label
                    val iconRes = when(nutrition.label.lowercase()) {
                        "calories" -> R.drawable.caloriecicular
                        "protein" -> R.drawable.proteincicular
                        "chất béo" -> R.drawable.fatcicular
                        "carb" -> R.drawable.cardcicular
                        else -> R.drawable.heart // default icon
                    }

                    Image(
                        painter = painterResource(id = iconRes),
                        contentDescription = nutrition.label,
                        modifier = Modifier.size(35.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = nutrition.label,
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    Text(
                        text = "${nutrition.currentValue}${nutrition.unit}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(android.graphics.Color.parseColor(nutrition.progressColor))
                    )
                }
            }
        }
    }
}