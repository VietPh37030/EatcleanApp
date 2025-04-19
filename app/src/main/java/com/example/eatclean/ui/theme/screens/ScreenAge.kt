package com.example.eatclean.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eatclean.R
import com.example.eatclean.ui.theme.components.DaggerWheelPicker
import com.example.eatclean.viewmodels.OnboardingViewModel

@Composable
fun ScreenAge(viewModel: OnboardingViewModel) {
    val uiState = viewModel.uiState.collectAsState().value

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

        // Truyền giá trị hiện tại và callback cập nhật về ViewModel
        DaggerWheelPicker(
            selectedValue = uiState.age,  // MVVM: Lấy giá trị từ ViewModel
            onValueChange = { viewModel.updateAge(it) }
        )
    }
}