package com.example.eatclean.ui.theme.screens

import android.widget.NumberPicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.example.eatclean.R
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.CoroutineScope
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import com.example.eatclean.components.BMIIndicatorBar
import com.example.eatclean.components.DaggerWheelPicker
import com.example.eatclean.components.WeightScale
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.LazyListSnapperLayoutInfo
import dev.chrisbanes.snapper.rememberLazyListSnapperLayoutInfo
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlin.math.abs


@Preview(showBackground = true)
@Composable
fun ProgressBarP2() {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    var progress by remember { mutableStateOf(0.1f) }
    val totalPages = 3 // Số lượng câu hỏi
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        ScreenContent4()


    }

}

@Composable
fun ScreenContent3() {
    // 1. State để lưu giá trị đang chọn
    var selectedAge by remember { mutableStateOf("A") }
    // 2. Danh sách giá trị hiển thị
    val options = listOf("A", "B", "C", "D", "E")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.8f)
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.birthday_cake),
            contentDescription = "Main Image",
            modifier = Modifier
                .height(40.dp)
                .width(80.dp)
        )

        Text(
            text = "Bạn bao nhiêu tuổi?",
            color = Color.Black,
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp),
            modifier = Modifier
                .padding(16.dp)

        )

        DaggerWheelPicker()
    }
}

@Composable
fun ScreenContent4() {
    val heightInCm = 170 // Bạn có thể thay đổi chiều cao người dùng tại đây
    var weight by remember { mutableStateOf(70f) }
    val heightInM = heightInCm / 100f
    val bmi = weight / (heightInM * heightInM)

    val bmiStatus = when {
        bmi < 18.5 -> "Thiếu cân"
        bmi < 24.9 -> "Bình thường"
        bmi < 29.9 -> "Thừa cân"
        else -> "Béo phì"
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.8f)
            .padding(top=10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.electronic_scale),
            contentDescription = "Main Image",
            modifier = Modifier
                .height(40.dp)
                .width(80.dp)
        )

        Text(
            text = "Cân nặng của bạn là bao nhiêu ?",
            color = Color.Black,
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp),
            modifier = Modifier
                .padding(16.dp)

        )

        Row(
            modifier = Modifier.fillMaxWidth(0.35f)
        ) {
            Button(
                onClick = { /* Hành động cho nút đầu tiên */ },
                modifier = Modifier
                    .height(35.dp)
                    .weight(1f)
                    .padding(end = 0.dp),
                shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp, topEnd = 0.dp, bottomEnd = 0.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
            ) {
                Text("kg",fontSize = 12.sp)
            }
            Button(
                onClick = { /* Hành động cho nút thứ hai */ },
                modifier = Modifier
                    .height(35.dp)
                    .weight(1f)
                    .padding(start = 0.dp),

                shape = RoundedCornerShape(topStart = 0.dp, bottomStart = 0.dp, topEnd = 8.dp, bottomEnd = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text("lbs",fontSize = 12.sp)
            }
        }

        Text(
            text = "Chỉ số khối cơ thể (BMI) của bạn là ",
            color = Color.Black,
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 12.sp),
            modifier = Modifier

        )

        Text(
            text = String.format("%.1f", bmi),
            color = Color.Blue,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold),
            modifier = Modifier

        )

        Row(
            
        ) {
            Text(
                text = "BMI của bạn cho thấy là bạn đang ",
                color = Color.Black,
                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 12.sp),
                modifier = Modifier

            )

            Text(
                text = bmiStatus,
                color = Color.Black,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold),
                modifier = Modifier

            )
        }

        BMIIndicatorBar(bmi)
        WeightScale(
            initialWeight=weight,
            onWeightChange = { weight = it }
        )
    }
}

@Composable
fun BMIScreenFull() {
    var bmi by remember { mutableStateOf(0f) }
    var weight by remember { mutableStateOf(70f) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Chỉ số BMI")
        Spacer(Modifier.height(8.dp))
        BMIIndicatorBar(bmi = bmi)

        Spacer(Modifier.height(16.dp))
        Text("Điều chỉnh cân nặng:")
        Slider(
            value = bmi,
            onValueChange = { bmi = it },
            valueRange = 0f..40f,
            steps = 39, // bước nhảy = 1 đơn vị BMI
            modifier = Modifier.fillMaxWidth()
        )
    }
}
