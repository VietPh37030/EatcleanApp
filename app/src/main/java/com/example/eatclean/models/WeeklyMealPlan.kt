data class WeeklyMealPlan(
    val dayName: String,
    val totalCalories: Int,
    val totalProtein: Int,
    val totalCarbs: Int,
    val totalFat: Int,
    val meals: List<MealSummary>, // Sử dụng Meal hiện có thay vì chỉ lưu tên
    val isVip: Boolean = false
)