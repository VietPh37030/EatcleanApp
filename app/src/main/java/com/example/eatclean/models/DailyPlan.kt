package com.example.eatclean.models

import java.time.LocalDate

data class DailyPlan(
    val date: LocalDate,
    val meals: List<Meal>,
    val totalCalories: Int,
    val totalProtein: Int,
    val totalCarbs: Int,
    val totalFat: Int
)