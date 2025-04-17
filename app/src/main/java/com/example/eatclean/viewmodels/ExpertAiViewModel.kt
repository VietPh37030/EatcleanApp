package com.example.eatclean.ui.theme.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

import com.example.eatclean.models.Meal
import com.example.eatclean.repository.MealPlanAiRepository


class ExpertAiViewModel(private val repository: MealPlanAiRepository = MealPlanAiRepository()) : ViewModel() {
    val messages = mutableStateOf(repository.getMessages())
    val dailyPlans = mutableStateOf(repository.getDailyPlans())

    private var suggestedMeals: List<Meal>? = null

    fun sendMessage(userMessage: String) {
        suggestedMeals = repository.sendMessage(userMessage)
        messages.value = repository.getMessages() // Cập nhật danh sách tin nhắn
    }

    fun applySuggestedMealPlan() {
        suggestedMeals?.let { meals ->
            repository.applyMealPlan(meals)
            messages.value = repository.getMessages() // Cập nhật tin nhắn
            dailyPlans.value = repository.getDailyPlans() // Cập nhật kế hoạch ngày
            suggestedMeals = null
        }
    }
}