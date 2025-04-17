package com.example.eatclean.viewmodel

import WeeklyMealPlan
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope

import com.example.eatclean.repository.WeeklyPlanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeeklyPlanViewModel(
    private val repository: WeeklyPlanRepository
) : ViewModel() {
    private val _weeklyPlans = MutableStateFlow<List<WeeklyMealPlan>>(emptyList())
    val weeklyPlans: StateFlow<List<WeeklyMealPlan>> = _weeklyPlans.asStateFlow()

    init {
        loadWeeklyPlans()
    }

    private fun loadWeeklyPlans() {
        viewModelScope.launch {
            repository.getWeeklyPlans().collect { plans ->
                _weeklyPlans.value = plans
            }
        }
    }
}

class WeeklyPlanViewModelFactory(
    private val repository: WeeklyPlanRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeeklyPlanViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WeeklyPlanViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}