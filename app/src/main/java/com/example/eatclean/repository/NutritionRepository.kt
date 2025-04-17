package com.example.eatclean.repository



import com.example.eatclean.models.Nutrition
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate

class NutritionRepository {
    // Giả lập dữ liệu, sau này thay bằng API call
    fun getNutritionData(date: LocalDate): Flow<List<Nutrition>> = flow {
        emit(
            listOf(
                Nutrition(1000, 1700, "Calories", "kcal", "#FF6B6B"),
                Nutrition(300, 1700, "Protein", "g", "#3B82F6"),
                Nutrition(850, 1700, "Chất béo", "g", "#EAB308"),
                Nutrition(0, 1700, "Carb", "g", "#4DCB72")
            )
        )
    }

    fun getHeartHealthData(): Flow<List<Nutrition>> = flow {
        emit(
            listOf(
                Nutrition(1000, 300, "Cholesterol", "mg", "#F59E0B"),
                Nutrition(100, 300, "Omega-3", "mg", "#FB9B5B"),
                Nutrition(20, 300, "Chất xơ", "mg", "#FF6B6B"),
                Nutrition(1000, 300, "Nước", "mg", "#60A5FA")
            )
        )
    }
}