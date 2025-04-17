package com.example.eatclean.viewmodels

// D:\Project Android\Eatclean\app\src\main\java\com\example\eatclean\viewmodels\ProteinViewModel.kt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eatclean.models.ProteinDailyData
import com.example.eatclean.models.ProteinFood
import com.example.eatclean.models.ProteinInfo
import com.example.eatclean.repository.ProteinRepository

import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class ProteinViewModel(
    private val repository: ProteinRepository = ProteinRepository()
) : ViewModel() {

    val proteinFoods: StateFlow<List<ProteinFood>> = repository.proteinFoods
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    val dailyData: StateFlow<List<ProteinDailyData>> = repository.dailyData
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    val proteinInfos: StateFlow<List<ProteinInfo>> = repository.proteinInfos
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun getDailyGoal(): Int = repository.getDailyGoal()

    fun getConsumedToday(): Int = repository.getConsumedToday()

    fun getFormattedDateTime(food: ProteinFood): String = repository.getFormattedDateTime(food.consumedAt)
}