package com.example.eatclean.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.eatclean.models.DayInfo
import com.example.eatclean.models.Meal
import com.example.eatclean.models.Nutrition
import com.example.eatclean.repository.MealRepository
import com.example.eatclean.repository.NutritionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

class DietPlanViewModel(
    private val nutritionRepository: NutritionRepository,
    private val mealRepository: MealRepository
) : ViewModel() {
    
    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()

    private val _daysOfWeek = MutableStateFlow<List<DayInfo>>(emptyList())
    val daysOfWeek: StateFlow<List<DayInfo>> = _daysOfWeek.asStateFlow()

    private val _nutritionSummary = MutableStateFlow<List<Nutrition>>(emptyList())
    val nutritionSummary: StateFlow<List<Nutrition>> = _nutritionSummary.asStateFlow()

    private val _meals = MutableStateFlow<List<Meal>>(emptyList())
    val meals: StateFlow<List<Meal>> = _meals.asStateFlow()

    init {
        updateWeekDays()
        loadNutritionSummary(LocalDate.now())
        loadMeals(LocalDate.now())
    }

    fun selectDate(date: LocalDate) {
        _selectedDate.value = date
        loadNutritionSummary(date)
        loadMeals(date)
    }

    private fun updateWeekDays() {
        val currentDate = LocalDate.now()
        val startOfWeek = currentDate.with(java.time.DayOfWeek.MONDAY)
        val days = (0..6).map { offset ->
            val date = startOfWeek.plusDays(offset.toLong())
            val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("vi", "VN"))
            DayInfo(date, dayOfWeek, date.dayOfMonth)
        }
        _daysOfWeek.value = days
    }

    private fun loadNutritionSummary(date: LocalDate) {
        viewModelScope.launch {
            nutritionRepository.getNutritionData(date).collect { data ->
                _nutritionSummary.value = data
            }
        }
    }

    private fun loadMeals(date: LocalDate) {
        viewModelScope.launch {
            mealRepository.getMealsForDay(date).collect { meals ->
                _meals.value = meals
            }
        }
    }
}

class DietPlanViewModelFactory(
    private val nutritionRepository: NutritionRepository,
    private val mealRepository: MealRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DietPlanViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DietPlanViewModel(nutritionRepository, mealRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}