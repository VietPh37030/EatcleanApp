package com.example.eatclean.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eatclean.models.DayInfo
import com.example.eatclean.models.FoodRecord
import com.example.eatclean.models.Nutrition
import com.example.eatclean.repository.FoodRepository
import com.example.eatclean.repository.NutritionRepository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

class FollowScreenViewModel(
    private val nutritionRepository: NutritionRepository,
    private val foodRepository: FoodRepository
) : ViewModel() {

    private val _weekOffset = MutableStateFlow(0)
    val weekOffset: StateFlow<Int> = _weekOffset.asStateFlow()

    private val _daysOfWeek = MutableStateFlow<List<DayInfo>>(emptyList())
    val daysOfWeek: StateFlow<List<DayInfo>> = _daysOfWeek.asStateFlow()

    private val _nutritionData = MutableStateFlow<List<Nutrition>>(emptyList())
    val nutritionData: StateFlow<List<Nutrition>> = _nutritionData.asStateFlow()

    private val _heartHealthData = MutableStateFlow<List<Nutrition>>(emptyList())
    val heartHealthData: StateFlow<List<Nutrition>> = _heartHealthData.asStateFlow()

    private val _foodRecords = MutableStateFlow<List<FoodRecord>>(emptyList())
    val foodRecords: StateFlow<List<FoodRecord>> = _foodRecords.asStateFlow()

    init {
        updateWeekDays()
        loadNutritionData()
        loadHeartHealthData()
        loadFoodRecords(LocalDate.now())
    }

    fun updateWeekOffset(offset: Int) {
        _weekOffset.value = offset
        updateWeekDays()
    }

    private fun updateWeekDays() {
        val currentDate = LocalDate.now()
        val startOfWeek = currentDate.plusWeeks(_weekOffset.value.toLong())
            .with(java.time.DayOfWeek.MONDAY)
        val days = (0..6).map { offset ->
            val date = startOfWeek.plusDays(offset.toLong())
            val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("vi", "VN"))
            DayInfo(date, dayOfWeek, date.dayOfMonth)
        }
        _daysOfWeek.value = days
    }

    private fun loadNutritionData() {
        viewModelScope.launch {
            nutritionRepository.getNutritionData(LocalDate.now()).collect { data ->
                _nutritionData.value = data
            }
        }
    }

    private fun loadHeartHealthData() {
        viewModelScope.launch {
            nutritionRepository.getHeartHealthData().collect { data ->
                _heartHealthData.value = data
            }
        }
    }

    private fun loadFoodRecords(date: LocalDate) {
        viewModelScope.launch {
            foodRepository.getFoodRecords(date).collect { records ->
                _foodRecords.value = records
            }
        }
    }
}