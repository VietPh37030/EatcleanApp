package com.example.eatclean.ui.theme.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eatclean.R
import com.example.eatclean.ui.theme.EatcleanTheme


class Introduction : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EatcleanTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    IntroductionScreen()
                }
            }
        }
    }
}
@Composable
fun IntroductionScreen() {
    var progress by remember { mutableStateOf(0.0f) }
        Column (
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Spacer(modifier = Modifier.height(40.dp))
            // Logo cho ung dung
            Image(
                painter = painterResource(R.drawable.abc),
                contentDescription = "logo",
                modifier = Modifier.size(100.dp).padding(8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Text ten ung dumg
            Text(
                text = "EatClean",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            // description
            Text(
                text = "Năm mới, Bạn mới! Trở nên khoẻ mạnh hơn trong năm 2024!",
                fontSize = 19.sp,
                textAlign = TextAlign.Center,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal =5.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            //Contents
            Text(
                text = "Chào\uD83D\uDC4B Tôi là Chuyên gia Dinh dưỡng Cá nhân của bạn được hỗ trợ bởi AI. Tôi sẽ hỏi bạn một số câu hỏi để cá nhân hóa một kế hoạch ăn kiêng thông minh cho bạn.\"\n" +
                        "\n" +
                        "",
                fontSize = 19.sp,
                textAlign = TextAlign.Center,
                color = Color.Black,
                modifier = Modifier.padding(horizontal =5.dp)
            )
            Spacer(modifier = Modifier.height(25.dp))
            // description
            Text(
                text = "Nếu bạn có tài khoản , vui lòng đăng nhập tại đây.",
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                color = Color.Blue,
                modifier = Modifier.padding(horizontal =5.dp)
            )
            // Button dang nhap
            Button(
                onClick = { /* Chuyển sang màn tiếp theo */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp)
                ) {
                Text(text = "Tiep tuc")
            }
        }
}