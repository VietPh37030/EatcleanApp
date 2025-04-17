package com.example.eatclean.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eatclean.repository.MealRepository
import com.example.eatclean.repository.NutritionRepository
import com.example.eatclean.ui.components.*
import com.example.eatclean.ui.theme.EatcleanTheme
import com.example.eatclean.ui.theme.components.DayItem
import com.example.eatclean.ui.theme.components.MealSection
import com.example.eatclean.ui.theme.screens.WeeklyPlanScreenContent
import com.example.eatclean.viewmodel.DietPlanViewModel
import com.example.eatclean.viewmodel.DietPlanViewModelFactory
import com.example.eatclean.viewmodel.WeeklyPlanViewModel
import com.example.eatclean.viewmodel.WeeklyPlanViewModelFactory
import com.example.eatclean.repository.WeeklyPlanRepository

class DietPlanScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EatcleanTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    DietPlanScreenContainer()
                }
            }
        }
    }
}

@Composable
fun DietPlanScreenContainer() {
    var selectedTab by remember { mutableStateOf("daily") }
    
    val dietPlanViewModel: DietPlanViewModel = viewModel(
        factory = DietPlanViewModelFactory(
            NutritionRepository(),
            MealRepository()
        )
    )
    
    val weeklyPlanViewModel: WeeklyPlanViewModel = viewModel(
        factory = WeeklyPlanViewModelFactory(
            WeeklyPlanRepository()
        )
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Header(
            title = "EatClean",
            buttonText = "Dùng thử miễn phí",
            onButtonClick = { /* TODO */ }
        )

        // Tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { selectedTab = "weekly" },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedTab == "weekly") Color(0xFF00BCD4) else Color.Gray
                )
            ) {
                Text(
                    text = "Kế hoạch hàng tuần",
                    color = Color.White,
                    modifier = Modifier.padding(8.dp)
                )
            }
            Button(
                onClick = { selectedTab = "daily" },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedTab == "daily") Color(0xFF4CAF50) else Color.Gray
                )
            ) {
                Text(
                    text = "Kế hoạch hàng ngày",
                    color = Color.White,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        // Content
        Box(modifier = Modifier.weight(1f)) {
            when (selectedTab) {
                "daily" -> DietPlanScreenContent(dietPlanViewModel)
                "weekly" -> WeeklyPlanScreenContent(weeklyPlanViewModel)
            }
        }
    }
}

@Composable
fun DietPlanScreenContent(viewModel: DietPlanViewModel) {
    val daysOfWeek by viewModel.daysOfWeek.collectAsState()
    val selectedDate by viewModel.selectedDate.collectAsState()
    val nutritionSummary by viewModel.nutritionSummary.collectAsState()
    val meals by viewModel.meals.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            // Calendar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
                    .background(color = Color(0xFFF3F4F6)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                daysOfWeek.forEach { day ->
                    DayItem(
                        day = day,
                        isSelected = day.date == selectedDate,
                        onClick = { day.date?.let { viewModel.selectDate(it) } }
                    )
                }
            }
        }

        item {
            NutritionSummary(nutritionData = nutritionSummary)
        }

        items(meals) { meal ->
            MealSection(meal = meal)
        }
    }
}

