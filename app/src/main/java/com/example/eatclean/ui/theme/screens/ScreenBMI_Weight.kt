package com.example.eatclean.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eatclean.R
import com.example.eatclean.ui.theme.components.BMIIndicatorBar
import com.example.eatclean.ui.theme.components.WeightScale
import com.example.eatclean.viewmodels.OnboardingViewModel

@Composable
fun ScreenBMI_Weight(
    viewModel: OnboardingViewModel
) {
    // MVVM: Sử dụng collectAsState để theo dõi thay đổi từ ViewModel
    val uiState = viewModel.uiState.collectAsState().value
    // MVVM: Lấy BMI từ ViewModel thay vì tính toán trong UI
    val bmi = viewModel.getBMI()
    val bmiStatus = viewModel.getBMIStatus()

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
                onClick = { viewModel.updateUnit("kg") },
                modifier = Modifier
                    .height(35.dp)
                    .weight(1f)
                    .padding(end = 0.dp),
                shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp, topEnd = 0.dp, bottomEnd = 0.dp),
                colors = ButtonDefaults.buttonColors( containerColor = if (uiState.unit == "kg") Color.Green else Color.Gray)
            ) {
                Text("kg",fontSize = 12.sp)
            }
            Button(
                onClick = { viewModel.updateUnit("lbs") },
                modifier = Modifier
                    .height(35.dp)
                    .weight(1f)
                    .padding(start = 0.dp),

                shape = RoundedCornerShape(topStart = 0.dp, bottomStart = 0.dp, topEnd = 8.dp, bottomEnd = 8.dp),
                colors = ButtonDefaults.buttonColors( containerColor = if (uiState.unit == "lbs") Color.Green else Color.Gray)
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
            initialWeight = uiState.weight,
            unit = uiState.unit,
            onWeightChange = { viewModel.updateWeight(it) }  // MVVM: Cập nhật weight thông qua ViewModel
        )
    }
}