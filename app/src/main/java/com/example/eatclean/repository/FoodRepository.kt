package com.example.eatclean.repository


import com.example.eatclean.models.FoodRecord
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate

class FoodRepository {
    fun getFoodRecords(date: LocalDate): Flow<List<FoodRecord>> = flow {
        // Giả lập, thay bằng API call sau
        emit(emptyList())
    }
}