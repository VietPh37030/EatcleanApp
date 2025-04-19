package com.example.eatclean.viewmodels


import androidx.lifecycle.ViewModel
import com.example.eatclean.models.ProgressState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged

class OnboardingViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProgressState())
    val uiState = _uiState.asStateFlow()

    fun updateAge(value: String) {
        // Chỉ cập nhật khi giá trị thực sự thay đổi
        if (_uiState.value.age != value) {
            _uiState.value = _uiState.value.copy(age = value)
        }
    }

    fun updateWeight(value: Float) {
        _uiState.value = _uiState.value.copy(weight = value)
    }

    fun updateUnit(value: String) {
        val currentWeight = _uiState.value.weight
        val currentUnit = _uiState.value.unit

        // Nếu đơn vị thực sự thay đổi, thực hiện chuyển đổi
        if (currentUnit != value) {
            val newWeight = if (value == "kg" && currentUnit == "lbs") {
                // Chuyển từ lbs sang kg: 1 lbs = 0.453592 kg
                currentWeight * 0.453592f
            } else if (value == "lbs" && currentUnit == "kg") {
                // Chuyển từ kg sang lbs: 1 kg = 2.20462 lbs
                currentWeight * 2.20462f
            } else {
                // Giữ nguyên nếu không cần chuyển đổi
                currentWeight
            }

            // Cập nhật cả đơn vị và giá trị cân nặng
            _uiState.value = _uiState.value.copy(
                unit = value,
                weight = newWeight
            )
        }
    }

    fun updateCurrentPage(page: Int, totalPages: Int) {
        _uiState.value = _uiState.value.copy(
            currentPage = page,
            progress = (page + 1) / totalPages.toFloat()
        )
    }

    fun nextPage(totalPages: Int) {
        val currentPage = _uiState.value.currentPage
        if (currentPage < totalPages - 1) {
            val nextPage = currentPage + 1
            _uiState.value = _uiState.value.copy(
                currentPage = nextPage,
                progress = (nextPage + 1) / totalPages.toFloat()
            )
        }
    }

    fun previousPage() {
        val currentPage = _uiState.value.currentPage
        if (currentPage > 0) {
            val prevPage = currentPage - 1
            _uiState.value = _uiState.value.copy(
                currentPage = prevPage,
                progress = (prevPage + 1) / 3f
            )
        }
    }

    // MVVM: Logic tính toán BMI được chuyển vào ViewModel
    fun getBMI(): Float {
        val weight = _uiState.value.weight
        val height = _uiState.value.heightInCm / 100f

        // Nếu đơn vị là lbs, chuyển đổi về kg trước khi tính
        val weightInKg = if (_uiState.value.unit == "lbs") {
            weight * 0.453592f
        } else {
            weight
        }

        return weightInKg / (height * height)
    }

    fun getBMIStatus(): String {
        val bmi = getBMI()
        return when {
            bmi < 18.5 -> "Thiếu cân"
            bmi < 24.9 -> "Bình thường"
            bmi < 29.9 -> "Thừa cân"
            else -> "Béo phì"
        }
    }
}