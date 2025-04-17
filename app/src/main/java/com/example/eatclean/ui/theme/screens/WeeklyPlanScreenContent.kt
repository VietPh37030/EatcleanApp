package com.example.eatclean.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eatclean.ui.theme.components.WeeklyDayCard
import com.example.eatclean.viewmodel.WeeklyPlanViewModel

@Composable
fun WeeklyPlanScreenContent(viewModel: WeeklyPlanViewModel) {
    val weeklyPlans = viewModel.weeklyPlans.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = "Kế hoạch dinh dưỡng hàng tuần",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
        }

        items(weeklyPlans.value) { dayPlan ->
            WeeklyDayCard(
                dayPlan = dayPlan,
                onClick = {
                    if (dayPlan.isVip) {
                        println("Cho phép chỉnh sửa thực đơn ${dayPlan.dayName}")
                    } else {
                        println("Chuyển sang màn hình thanh toán")
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}