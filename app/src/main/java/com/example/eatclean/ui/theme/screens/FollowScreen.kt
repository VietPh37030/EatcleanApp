package com.example.eatclean.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eatclean.R
import com.example.eatclean.repository.FoodRepository
import com.example.eatclean.repository.NutritionRepository
import com.example.eatclean.ui.components.CircularNutri
import com.example.eatclean.ui.theme.EatcleanTheme
import com.example.eatclean.ui.theme.components.NutritionItem
import com.example.eatclean.viewmodel.FollowScreenViewModel
import com.example.eatclean.viewmodel.FollowScreenViewModelFactory
import java.time.LocalDate

class FollowScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EatcleanTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    FollowScreenContent()
                }
            }
        }
    }
}

@Composable
fun FollowScreenContent(
    viewModel: FollowScreenViewModel = viewModel(
        factory = FollowScreenViewModelFactory(
            NutritionRepository(),
            FoodRepository()
        )
    )
) {
    val daysOfWeek by viewModel.daysOfWeek.collectAsState()
    val nutritionData by viewModel.nutritionData.collectAsState()
    val heartHealthData by viewModel.heartHealthData.collectAsState()
    val foodRecords by viewModel.foodRecords.collectAsState()
    val currentDate = LocalDate.now()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(androidx.compose.foundation.rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "EatClean",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Green
            )
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF97316),
                    contentColor = Color.White
                ),
                modifier = Modifier.clip(RoundedCornerShape(20.dp))
            ) {
                Text(text = "Dùng thử miễn phí", fontSize = 14.sp)
            }
        }

        // Calendar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .background(color = Color(0xF3F4F6FF)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { viewModel.updateWeekOffset(viewModel.weekOffset.value - 1) },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.left),
                    contentDescription = "Previous week",
                    tint = Color.Black
                )
            }
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                daysOfWeek.forEach { day ->
                    val isToday = day.date == currentDate
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(60.dp)
                            .padding(horizontal = 4.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isToday) Color(0xFF00B4C4) else Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            day.dayOfWeek?.let {
                                Text(
                                    text = it,
                                    color = if (isToday) Color.White else Color.Black,
                                    fontSize = 13.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                            Text(
                                text = day.dayOfMonth.toString(),
                                color = if (isToday) Color.White else Color.Black,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
            IconButton(
                onClick = { viewModel.updateWeekOffset(viewModel.weekOffset.value + 1) },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.right),
                    contentDescription = "Next week",
                    tint = Color.Black
                )
            }
        }

        // Food Records
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Thực phẩm đã ghi nhận", color = Color.Black, fontSize = 16.sp)
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6)),
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .size(30.dp)
                    .clip(CircleShape),
                contentPadding = PaddingValues(0.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.right2),
                    contentDescription = "profile icon",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(color = Color(0xFFF3F4F6))
                .clip(RoundedCornerShape(16.dp))
        ) {
            if (foodRecords.isEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.cat),
                        contentDescription = "Ảnh thực phẩm ghi nhận",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                    )
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 10.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Bạn chưa ghi lại bất kì thực phẩm nào!")
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Bắt đầu ghi lại thực phẩm bằng cách nhấn nút ghi lại!")
                    }
                }
            } else {
                // Hiển thị danh sách thực phẩm khi có dữ liệu
                // Có thể thêm LazyColumn ở đây
            }
        }

        // Nutrition Overview
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(color = Color(0xFFF3F4F6))
        ) {
            Column {
                Row(
                    modifier = Modifier.padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.doctor),
                        contentDescription = "Icon dinh dưỡng",
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "Tổng quan về dinh dưỡng",
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 18.sp
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    nutritionData.take(2).forEach { nutrition ->
                        CircularNutri(
                            currentValue = nutrition.currentValue,
                            targetValue = nutrition.targetValue,
                            label = nutrition.label,
                            color = Color(android.graphics.Color.parseColor(nutrition.progressColor))
                        )
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    nutritionData.drop(2).forEach { nutrition ->
                        CircularNutri(
                            currentValue = nutrition.currentValue,
                            targetValue = nutrition.targetValue,
                            label = nutrition.label,
                            color = Color(android.graphics.Color.parseColor(nutrition.progressColor))
                        )
                    }
                }
            }
        }

        // Heart Health
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(color = Color(0xFFF3F4F6))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.heart),
                        contentDescription = "logo",
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "Sức khỏe tim mạch",
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Column {
                    heartHealthData.forEachIndexed { index, nutrition ->
                        NutritionItem(
                            iconRes = when (index) {
                                0 -> R.drawable.ltd
                                1 -> R.drawable.omega3
                                2 -> R.drawable.fibber
                                else -> R.drawable.water
                            },
                            name = nutrition.label,
                            currentValue = nutrition.currentValue,
                            targetValue = nutrition.targetValue,
                            unit = nutrition.unit,
                            progressColor = Color(android.graphics.Color.parseColor(nutrition.progressColor))
                        )
                        if (index < heartHealthData.size - 1) {
                            Divider(color = Color.LightGray, thickness = 1.dp)
                        }
                    }
                }
            }
        }
    }
}