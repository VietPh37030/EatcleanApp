package com.example.eatclean.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eatclean.repository.MealRepository
import com.example.eatclean.repository.NutritionRepository
import com.example.eatclean.ui.components.*
import com.example.eatclean.ui.theme.EatcleanTheme
import com.example.eatclean.ui.theme.components.DayItem
import com.example.eatclean.ui.theme.components.MealSection
import com.example.eatclean.viewmodel.DietPlanViewModel
import com.example.eatclean.viewmodel.DietPlanViewModelFactory

class DietPlanScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EatcleanTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    DietPlanScreenContent()
                }
            }
        }
    }
}

@Composable
fun DietPlanScreenContent(
    viewModel: DietPlanViewModel = viewModel(
        factory = DietPlanViewModelFactory(
            NutritionRepository(),
            MealRepository()
        )
    )
) {
    val daysOfWeek by viewModel.daysOfWeek.collectAsState()
    val selectedDate by viewModel.selectedDate.collectAsState()
    val nutritionSummary by viewModel.nutritionSummary.collectAsState()
    val meals by viewModel.meals.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(androidx.compose.foundation.rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Header(
            title = "EatClean",
            buttonText = "Dùng thử miễn phí",
            onButtonClick = { /* TODO */ }
        )

        // Tabs: Kế hoạch hàng tuần và hàng ngày
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Button(onClick = { /* TODO */ }
            ) {
                Text(
                    text = "Kế hoạch hàng tuần",
                    color = Color.Black,
                    modifier = Modifier.padding(8.dp)
                )
            }
            Button(onClick = { /* TODO */ },
                modifier = Modifier.clip(RoundedCornerShape(5.dp)).background(color = Color(0xFFc96442))

            ) {
                Text(
                    text = "Kế hoạch hàng ngày",
                    color = Color.Gray,
                    modifier = Modifier.padding(8.dp)
                )
            }

        }

        // Calendar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .background(color = Color(0xF3F4F6FF)),
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

        // Nutrition Summary
        NutritionSummary(nutritionData = nutritionSummary)

        // Meals (bao gồm HealthTip)
        meals.forEach { meal ->
            MealSection(meal = meal)
        }
    }
}