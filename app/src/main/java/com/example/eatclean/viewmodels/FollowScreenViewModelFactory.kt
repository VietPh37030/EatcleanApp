package com.example.eatclean.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.eatclean.repository.FoodRepository
import com.example.eatclean.repository.NutritionRepository
import com.example.eatclean.viewmodel.FollowScreenViewModel

class FollowScreenViewModelFactory(
    private val nutritionRepository: NutritionRepository,
    private val foodRepository: FoodRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FollowScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FollowScreenViewModel(nutritionRepository, foodRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}