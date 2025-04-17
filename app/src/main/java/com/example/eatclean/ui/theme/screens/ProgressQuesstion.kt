package com.example.eatclean.ui.theme.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.eatclean.ui.theme.EatcleanTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.background
import androidx.compose.ui.*
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.example.eatclean.R
import kotlinx.coroutines.CoroutineScope


class ProgressQuesstion : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EatcleanTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ViewPagerScreen()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProgressBarScreen() {
    ViewPagerScreen()
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ViewPagerScreen() {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    var progress by remember { mutableStateOf(0.1f) }
    val totalPages = 3 // Số lượng câu hỏi

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 500),
        label = "progressAnimation"
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(25.dp)
                .padding(5.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.CenterStart
        ) {
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxSize(),
                color = Color.Magenta,
                trackColor = Color.LightGray
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // Icon back ở góc trái
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .align(Alignment.TopStart) // Căn icon về bên trái
                    .clickable {  updateProgressAndPreviousPage(pagerState, coroutineScope, totalPages) {
                        progress = it
                    }}
            )

            // Column nằm giữa toàn bộ màn hình
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.Center)
            ) {
                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(bottom = 10.dp)
                ) {
                    Text(
                        text = "Eat",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 30.sp),

                    )
                    Text(
                        text = "Clean",
                        color = Color.Green,
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 30.sp),


                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Main Image",
                    modifier = Modifier
                        .height(100.dp)
                        .width(250.dp)

                )
            }
        }

        // Nội dung câu hỏi
        HorizontalPager(
            count = totalPages,
            state = pagerState,
            userScrollEnabled = false // <-- Chỉ cho chuyển khi bấm nút hoặc icon
        ) { page ->
            when (page) {
                0 -> ScreenContent3()
                1 -> ScreenContent4()
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top= 15.dp),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = { updateProgressAndNextPage(pagerState, coroutineScope, totalPages, 0.1f) {
                    progress = it
                }},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50)
                ),
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier
                    .fillMaxWidth(0.7f)
            ) {
                Text(
                    text = "Tiếp tục",
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }

    }
}


fun updateProgressAndNextPage(
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    totalPages: Int,
    progressIncrement: Float,
    updateProgress: (Float) -> Unit
) {
    val nextPage = pagerState.currentPage + 1
    if (nextPage < totalPages) {
        coroutineScope.launch {
            pagerState.animateScrollToPage(nextPage)
            updateProgress((nextPage + 1) / totalPages.toFloat()) // Cập nhật tiến độ
        }
    }
}

fun updateProgressAndPreviousPage(
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    totalPages: Int,
    updateProgress: (Float) -> Unit
) {
    val previousPage = pagerState.currentPage - 1
    if (previousPage >= 0) {
        coroutineScope.launch {
            pagerState.animateScrollToPage(previousPage)
            updateProgress((previousPage + 1) / totalPages.toFloat())
        }
    }
}
