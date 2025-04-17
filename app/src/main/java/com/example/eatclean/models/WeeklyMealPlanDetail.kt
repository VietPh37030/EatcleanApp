data class WeeklyMealPlanDetail(
    val dayName: String,
    val totalCalories: Int,
    val totalProtein: Int,
    val totalCarbs: Int,
    val totalFat: Int,
    val meals: List<MealSummary>, // Sử dụng MealSummary thay vì mealNames
    val isVip: Boolean = false
)

// MealSummary có thể tạo từ Meal đầy đủ
data class MealSummary(
    val mealType: String,
    val description: String,
    val calories: Int
)