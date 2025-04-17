package com.example.eatclean.repository

import com.example.eatclean.models.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class MealPlanAiRepository {
    private val dailyPlans = mutableListOf<DailyPlan>()
    private val messages = mutableListOf<Message>()

    init {
        // Fake data cho tin nhắn ban đầu
        messages.add(
            Message(
                content = "Chào Việt, tôi là trợ lý dinh dưỡng thông minh của bạn. Tôi đã bàn. Tôi đã bàn 2!. Tôi có 179 cm và nặng 59,3 kg. Tôi cần trả lời bài kiểm tra về lịch sử ăn uống của bạn để đề xuất các phương án phù hợp nhé. Bạn muốn kiểm tra về lịch sử ăn uống hay bất kỳ ý tưởng dinh dưỡng nào khác?",
                isUser = false,
                timestamp = getCurrentTime()
            )
        )
    }

    fun getMessages(): List<Message> = messages.toList()
    
    fun getDailyPlans(): List<DailyPlan> = dailyPlans.toList()

    fun sendMessage(userMessage: String): List<Meal> {
        messages.add(Message(content = userMessage, isUser = true, timestamp = getCurrentTime()))

        // Fake data cho gợi ý thực đơn
        val aiResponse = "Dựa trên dữ liệu của bạn, đây là thực đơn gợi ý cho ngày mai:\n" +
                "- Bữa sáng: Yến mạch với chuối và hạt chia (300 kcal, 10g protein, 5g fat, 45g carbs)\n" +
                "- Bữa trưa: Cơm gạo lứt, ức gà áp chảo, salad rau (500 kcal, 30g protein, 10g fat, 60g carbs)\n" +
                "- Bữa tối: Cá hồi nướng, khoai lang, bông cải xanh (400 kcal, 25g protein, 15g fat, 40g carbs)"
        messages.add(Message(content = aiResponse, isUser = false, timestamp = getCurrentTime()))

        val suggestedMeals = listOf(
            Meal(
                mealType = "Breakfast",
                date = LocalDate.now().plusDays(1),
                calories = 300,
                protein = 10,
                fat = 5,
                carbs = 45,
                items = listOf(
                    MealItem(name = "Yến mạch với chuối và hạt chia", quantity = "1 bát")
                ),
                healthTip = HealthTip(
                    preparationInstructions = "Trộn yến mạch với nước nóng, thêm chuối thái lát và hạt chia.",
                    healthBenefits = "Cung cấp năng lượng bền vững và hỗ trợ tiêu hóa."
                )
            ),
            Meal(
                mealType = "Lunch",
                date = LocalDate.now().plusDays(1),
                calories = 500,
                protein = 30,
                fat = 10,
                carbs = 60,
                items = listOf(
                    MealItem(name = "Cơm gạo lứt", quantity = "1 chén"),
                    MealItem(name = "Ức gà áp chảo", quantity = "100g"),
                    MealItem(name = "Salad rau", quantity = "1 đĩa nhỏ")
                ),
                healthTip = HealthTip(
                    preparationInstructions = "Áp chảo ức gà với ít dầu ô liu, ăn kèm cơm gạo lứt và salad.",
                    healthBenefits = "Giàu protein và chất xơ, hỗ trợ tăng cơ và no lâu."
                )
            ),
            Meal(
                mealType = "Dinner",
                date = LocalDate.now().plusDays(1),
                calories = 400,
                protein = 25,
                fat = 15,
                carbs = 40,
                items = listOf(
                    MealItem(name = "Cá hồi nướng", quantity = "100g"),
                    MealItem(name = "Khoai lang", quantity = "1 củ nhỏ"),
                    MealItem(name = "Bông cải xanh", quantity = "1/2 chén")
                ),
                healthTip = HealthTip(
                    preparationInstructions = "Nướng cá hồi với gia vị nhẹ, luộc khoai lang và bông cải xanh.",
                    healthBenefits = "Cung cấp omega-3 và vitamin, tốt cho tim mạch."
                )
            )
        )

        messages.add(
            Message(
                content = "Bạn có muốn áp dụng thực đơn này vào kế hoạch ngày mai không? Nhấn 'Áp dụng' để lưu.",
                isUser = false,
                timestamp = getCurrentTime()
            )
        )
        return suggestedMeals
    }

    fun applyMealPlan(meals: List<Meal>) {
        val date = meals.first().date
        val totalCalories = meals.sumOf { it.calories }
        val totalProtein = meals.sumOf { it.protein }
        val totalCarbs = meals.sumOf { it.carbs }
        val totalFat = meals.sumOf { it.fat }

        dailyPlans.removeAll { it.date == date }
        dailyPlans.add(
            DailyPlan(
                date = date,
                meals = meals,
                totalCalories = totalCalories,
                totalProtein = totalProtein,
                totalCarbs = totalCarbs,
                totalFat = totalFat
            )
        )

        messages.add(
            Message(
                content = "Thực đơn đã được áp dụng vào kế hoạch ngày ${date}!",
                isUser = false,
                timestamp = getCurrentTime()
            )
        )
    }

    private fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(Date())
    }
}