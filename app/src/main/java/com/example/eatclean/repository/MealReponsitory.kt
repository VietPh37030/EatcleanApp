package com.example.eatclean.repository

import com.example.eatclean.models.HealthTip
import com.example.eatclean.models.Meal
import com.example.eatclean.models.MealItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate



class MealRepository  {
    fun getMealsForDay(date: LocalDate):Flow<List<Meal>> = flow {
        emit(
            listOf(
                Meal(
                    mealType = "Bữa sáng",
                    date = date,
                    calories = 1000,
                    protein = 100,
                    fat = 100,
                    carbs = 100,
                    items = listOf(
                        MealItem("Bánh mì ốp la", "2 lát bánh mì nguyên cám"),
                        MealItem("Quả trứng", "2 quả"),
                        MealItem("Rau xà lách", "50g"),
                        MealItem("Quả cà chua", "1 quả"),
                        MealItem("Muỗng canh dầu ô liu", "1 muỗng")
                    ),
                    healthTip = HealthTip(
                        preparationInstructions = "Chiên trứng trong dầu ô liu, nướng bánh mì, sau đó cho trứng lên bánh và thêm rau xà lách, cà chua.",
                        healthBenefits = "Cung cấp protein và chất xơ, hỗ trợ giảm cân."
                    )
                ),
                Meal(
                    mealType = "Bữa trưa",
                    date = date,
                    calories = 600,
                    protein = 25,
                    fat = 15,
                    carbs = 70,
                    items = listOf(
                        MealItem("Cơm gạo lứt", "1 chén"),
                        MealItem("Ức gà nướng", "100g"),
                        MealItem("Rau luộc", "50g")
                    ),
                    healthTip = HealthTip(
                        preparationInstructions = "Nấu cơm gạo lứt, nướng ức gà với gia vị nhẹ, luộc rau và dùng kèm.",
                        healthBenefits = "Cung cấp năng lượng bền vững, hỗ trợ cơ bắp."
                    )
                ),
            )
        )
    }

}