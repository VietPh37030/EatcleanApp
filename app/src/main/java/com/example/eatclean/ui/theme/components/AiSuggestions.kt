package com.example.eatclean.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eatclean.R // Giả sử bạn có resource cho hình ảnh

@Composable
fun AiSuggestions(
    viewModel: ExpertAiViewModel,
    modifier: Modifier = Modifier
) {
    // Danh sách gợi ý của AI (có thể mở rộng nếu cần nhiều gợi ý hơn)
    val suggestions = listOf(
        "Hôm nay tôi ăn cơm chứ",
        "Tôi cần ăn gì để tăng cân",
        "Thực đơn giảm cân 1 tuần",
        "Tôi nên ăn bao nhiêu calo mỗi ngày"
    )

    // LazyRow để hiển thị danh sách gợi ý dưới dạng cuộn ngang
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp) // Khoảng cách giữa các gợi ý
    ) {
        items(suggestions) { suggestion ->
            // Row để chứa hình ảnh và nút gợi ý
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 4.dp)
            ) {
                // Hình ảnh nhỏ trước gợi ý (giả sử bạn có một drawable resource)
                Image(
                    painter = painterResource(id = R.drawable.ai), // Thay bằng resource thực tế của bạn
                    contentDescription = "Suggestion Icon",
                    modifier = Modifier
                        .size(24.dp) // Kích thước hình ảnh nhỏ
                        .padding(end = 4.dp)
                )

                // Nút gợi ý với màu xanh lá cây và hình vuông
                Button(
                    onClick = { viewModel.sendMessage(suggestion) },
                    modifier = Modifier
                        .height(40.dp) // Chiều cao cố định để tạo hình vuông
                        .widthIn(min = 120.dp), // Chiều rộng tối thiểu, tự mở rộng theo nội dung
                    shape = RoundedCornerShape(0.dp), // Hình vuông (góc bo = 0)
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50) // Màu xanh lá cây
                    )
                ) {
                    Text(
                        text = suggestion,
                        fontSize = 12.sp,
                        color = Color.White,
                        maxLines = 1, // Chỉ hiển thị 1 dòng
                        overflow = TextOverflow.Ellipsis // Nếu văn bản dài, hiển thị "..." ở cuối
                    )
                }
            }
        }
    }
}