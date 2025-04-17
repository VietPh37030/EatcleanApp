package com.example.eatclean.models

import java.time.LocalDate

data class Meal(
    val mealType:String,// "Breakfast", "Lunch", "Dinner", "Snack"
    val date: LocalDate,
    val calories: Int,
    val protein: Int,
    val fat: Int,
    val carbs: Int,
    val items: List<MealItem>,
    val healthTip: HealthTip
)

data  class MealItem (
    val  name:String,
    val quantity:String
)

