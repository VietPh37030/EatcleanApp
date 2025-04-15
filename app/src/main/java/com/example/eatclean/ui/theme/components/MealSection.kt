package com.example.eatclean.ui.theme.components



import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eatclean.R
import com.example.eatclean.models.Meal
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Edit
import androidx.compose.ui.text.style.TextOverflow


@Composable
fun MealSection(
    meal: Meal,
    modifier: Modifier = Modifier,
    onAiReplace: () -> Unit = {},
    onRecordFood: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFF3F4F6))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = meal.mealType,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Calories
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFFFEBEB))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "${meal.calories} kcal",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF6B6B)
                    )
                }

                // Protein
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF4FC3F7))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.protein),
                        contentDescription = "Protein icon",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${meal.protein}g",
                        fontSize = 14.sp,
                        color = Color(0xFFFBFFFC)
                    )
                }

                // Fat
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFFFFD54F))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.fat),
                        contentDescription = "Fat icon",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${meal.fat}g",
                        fontSize = 14.sp,
                        color = Color(0xFFFBFFFC)
                    )
                }

                // Carbs
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFFF8A65))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.carb),
                        contentDescription = "Carbs icon",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${meal.carbs}g",
                        fontSize = 14.sp,
                        color = Color(0xFFFBFFFC)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            meal.items.forEach { item ->
                Text(
                    text = item.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(vertical = 2.dp)
                )
                Text(
                    text = item.quantity,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "★ Cách chuẩn bị",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = meal.healthTip.preparationInstructions,
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(top = 4.dp)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "★ Lợi ích sức khỏe",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = meal.healthTip.healthBenefits,
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(top = 4.dp)
            )
            
            // Thêm spacing trước các nút
            Spacer(modifier = Modifier.height(16.dp))
            
            // Thêm 2 nút
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = onAiReplace,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                        .height(48.dp), // Thêm chiều cao cố định
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4FC3F7)
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(), // Thêm fillMaxWidth
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center // Căn giữa các phần tử
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "AI Icon",
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Thay bằng AI",
                            color = Color.White
                        )
                    }
                }

                Button(
                    onClick = onRecordFood,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                        .height(48.dp), // Thêm chiều cao cố định
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4DCB72)
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(), // Thêm fillMaxWidth
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center // Căn giữa các phần tử
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Record Icon",
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Ghi lại thực đơn",
                            color = Color.White,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                    }
                }
            }
        }
    }
}