// File: com/example/eatclean/repository/WeeklyPlanRepository.kt
package com.example.eatclean.repository

import MealSummary
import WeeklyMealPlan
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WeeklyPlanRepository {
    fun getWeeklyPlans(): Flow<List<WeeklyMealPlan>> = flow {
        emit(
            listOf(
                WeeklyMealPlan(
                    dayName = "Thứ Hai",
                    totalCalories = 2000,
                    totalProtein = 100,
                    totalCarbs = 250,
                    totalFat = 70,
                    meals = listOf(
                        MealSummary(
                            mealType = "Bữa sáng",
                            description = "Yến mạch với sữa và trái cây",
                            calories = 450
                        ),
                        MealSummary(
                            mealType = "Bữa trưa",
                            description = "Gà nướng với salad",
                            calories = 650
                        ),
                        MealSummary(
                            mealType = "Bữa tối",
                            description = "Cá hồi nướng với rau",
                            calories = 550
                        ),
                        MealSummary(
                            mealType = "Ăn vặt",
                            description = "Cá hồi nướng với rau",
                            calories = 550
                        )
                    ),
                    isVip = true
                ),
                WeeklyMealPlan(
                    dayName = "Thứ Ba",
                    totalCalories = 1900,
                    totalProtein = 90,
                    totalCarbs = 240,
                    totalFat = 65,
                    meals = listOf(
                        MealSummary(
                            mealType = "Bữa sáng",
                            description = "Smoothie protein với chuối và dâu",
                            calories = 400
                        ),
                        MealSummary(
                            mealType = "Bữa trưa",
                            description = "Salad trộn thịt gà",
                            calories = 550
                        ),
                        MealSummary(
                            mealType = "Bữa tối",
                            description = "Bò xào rau củ",
                            calories = 600
                        )
                    ),
                    isVip = false
                )
                // Add more days if needed
            )
        )
    }
}