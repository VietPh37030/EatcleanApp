package com.example.eatclean.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Header(
    title: String = "EatClean",
    buttonText: String = "Dùng thử miễn phí",
    onButtonClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF4CAF50), // Xanh lá đậm
                        Color(0xFF81C784)  // Xanh lá nhạt
                    )
                )
            )
            .padding(vertical = 16.dp, horizontal = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Tiêu đề
            Text(
                text = title,
                fontSize = 24.sp, // Tăng kích thước chữ cho nổi bật
                fontWeight = FontWeight.ExtraBold,
                color = Color.White, // Chuyển sang trắng để nổi trên gradient
                modifier = Modifier
                    .shadow(2.dp, RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )

            // Nút
            Button(
                onClick = onButtonClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF9800), // Màu cam tươi sáng
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp)) // Bo tròn mạnh hơn
                    .shadow(4.dp, RoundedCornerShape(50.dp)) // Thêm bóng
                    .border(1.dp, Color.White.copy(alpha = 0.3f), RoundedCornerShape(50.dp)) // Viền nhẹ
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 2.dp,
                    pressedElevation = 6.dp
                )
            ) {
                Text(
                    text = buttonText,
                    fontSize = 16.sp, // Tăng kích thước chữ nút
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}