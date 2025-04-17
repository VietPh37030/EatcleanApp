package com.example.eatclean.ui.theme.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eatclean.models.Message
import com.example.eatclean.ui.components.Header
import com.example.eatclean.ui.theme.EatcleanTheme

class ExpertAiScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EatcleanTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ExpertAiScreenContent()
                }
            }
        }
    }
}

@Composable
fun ExpertAiScreenContent(viewModel: ExpertAiViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Header(
            title = "EatClean",
            buttonText = "Dùng thử miễn phí",
            onButtonClick = { /* TODO: Xử lý khi nhấn nút */ }
        )

        // Danh sách tin nhắn
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            reverseLayout = false
        ) {
            items(viewModel.messages.value) { message ->
                MessageItem(message)
            }
        }

        // Nút "Áp dụng" nếu có gợi ý thực đơn
        if (viewModel.messages.value.any { it.content.contains("Bạn có muốn áp dụng thực đơn này") }) {
            Button(
                onClick = { viewModel.applySuggestedMealPlan() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp) ,shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF9C27B0) // Màu xanh lá cây
                        )
            ) {
                Text("Áp dụng")
            }
        }

        // Component gợi ý của AI (cuộn ngang)
        AiSuggestions(viewModel = viewModel)

        // Ô nhập liệu và nút gửi
        var userInput by remember { mutableStateOf("") }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = userInput,
                onValueChange = { userInput = it },
                placeholder = { Text("Hãy đặt câu hỏi của bạn") },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                shape = RoundedCornerShape(16.dp)
            )
            Button(
                onClick = {
                    if (userInput.isNotBlank()) {
                        viewModel.sendMessage(userInput)
                        userInput = ""
                    }
                },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50) // Màu xanh lá cây
                )

            ) {
                Text("Gửi")
            }
        }
    }
}

@Composable
fun MessageItem(message: Message) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = if (message.isUser) Color(0xFFDCF8C6) else Color(0xFF2196F3),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(12.dp)
                .widthIn(max = 280.dp)
        ) {
            Text(
                text = message.content,
                color = if (message.isUser) Color.Black else Color.White,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = message.timestamp,
                color = if (message.isUser) Color.Gray else Color.White.copy(alpha = 0.7f),
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}