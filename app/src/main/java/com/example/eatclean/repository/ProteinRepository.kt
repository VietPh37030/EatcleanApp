package com.example.eatclean.repository

// D:\Project Android\Eatclean\app\src\main\java\com\example\eatclean\repositories\ProteinRepository.kt

import com.example.eatclean.models.ProteinDailyData
import com.example.eatclean.models.ProteinFood
import com.example.eatclean.models.ProteinInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ProteinRepository {
    private val _proteinFoods = MutableStateFlow<List<ProteinFood>>(
        listOf(
            ProteinFood(
                "1",
                "Bánh mì kẹp thịt nướng 290g",
                "290g",
                25.0,
                LocalDateTime.of(2025, 4, 15, 21, 49)
            ),
            ProteinFood(
                "2",
                "Bánh mì kẹp thịt heo nướng 100g",
                "100g",
                12.0,
                LocalDateTime.of(2025, 4, 2, 8, 0)
            ),
            ProteinFood(
                "3",
                "Rau xà lách 50g",
                "50g",
                0.7,
                LocalDateTime.of(2025, 4, 2, 8, 0)
            ),
            ProteinFood(
                "4",
                "Sốt mayonnaise 20g",
                "20g",
                0.2,
                LocalDateTime.of(2025, 4, 2, 8, 0)
            )
        )
    )
    val proteinFoods: StateFlow<List<ProteinFood>> = _proteinFoods

    private val _dailyData = MutableStateFlow<List<ProteinDailyData>>(
        listOf(
            ProteinDailyData("10/4", 0.0, 184.0),
            ProteinDailyData("11/4", 0.0, 184.0),
            ProteinDailyData("12/4", 0.0, 184.0),
            ProteinDailyData("13/4", 0.0, 184.0),
            ProteinDailyData("14/4", 0.0, 184.0),
            ProteinDailyData("15/4", 25.0, 184.0),
            ProteinDailyData("16/4", 0.0, 184.0)
        )
    )
    val dailyData: StateFlow<List<ProteinDailyData>> = _dailyData

    private val _proteinInfos = MutableStateFlow<List<ProteinInfo>>(
        listOf(
            ProteinInfo(
                "Protein là gì?",
                "Protein là một chất dinh dưỡng đa lượng được tạo thành từ các axit amin, cần thiết cho việc xây dựng và sửa chữa mô, sản xuất enzyme và hormone, và hỗ trợ sự phát triển cơ bắp. Nó đóng vai trò quan trọng trong nhiều quá trình sinh học."
            ),
            ProteinInfo(
                "7 Siêu Thực Phẩm Giàu Protein",
                "Trứng, ức gà, cá hồi, đậu lăng, đậu phụ, sữa chua Hy Lạp, hạt diêm mạch"
            ),
            ProteinInfo(
                "Lợi ích Sức Khỏe",
                "Xây dựng cơ bắp, hỗ trợ làn da, sản xuất enzyme/hormone, cân bằng chất, tăng cường miễn dịch."
            ),
            ProteinInfo(
                "Bận Động Hành Dinh Dưỡng",
                "Kết hợp protein với carbohydrate, chất béo lành mạnh, và vitamin B."
            ),
            ProteinInfo(
                "Dấu Hiệu Bạn Đang Thiếu",
                "Mệt mỏi, yếu cơ, tốc móng tay chậm, vết thương lâu lành, tăng nguy cơ mắc bệnh."
            ),
            ProteinInfo(
                "Phủ Vọ Nhừng Hiểu Lầm",
                "Protein không gây hại nếu tiêu thụ đúng (đậu lăng, đậu phụ, quinoa là nguồn protein tốt)."
            ),
            ProteinInfo(
                "Tiêu Điểm Bổ Sung",
                "0.8g/kg trọng lượng (hoặc 1.6-2.2g/kg nếu hoạt động nhiều). Nguồn bổ sung: Whey protein, casein, hoặc thực vật."
            ),
            ProteinInfo(
                "Nhầm Lẫn Thức Men",
                "Protein từ thực vật cũng hiệu quả, nhưng cần chú ý chế độ để tránh thiếu vitamin K."
            )
        )
    )
    val proteinInfos: StateFlow<List<ProteinInfo>> = _proteinInfos

    fun getDailyGoal(): Int = 184

    fun getConsumedToday(): Int = 25

    fun getFormattedDateTime(dateTime: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("d/M, HH:mm")
        return dateTime.format(formatter)
    }
}