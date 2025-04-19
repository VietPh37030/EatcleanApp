package com.example.eatclean.ui.theme.components

import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlin.math.abs

@Composable
fun DaggerWheelPicker(
    selectedValue: String,
    onValueChange: (String) -> Unit
) {
    val lists = arrayListOf<Int>().apply {
        for (i in 1..100) {
            add(i)
        }
    }
    // Chỉ cập nhật initIndex khi cần thiết
    val initialIndex = remember(selectedValue) {
        try {
            val selectedAge = selectedValue.toIntOrNull() ?: 25
            lists.indexOf(selectedAge).takeIf { it >= 0 }
        } catch (e: Exception) {
            null
        }
    }

    WheelPicker(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        pickerMaxHeight = 250.dp,
        lists = lists,
        initIndex = initialIndex,
        onValueSelected = { selectedAge ->  // Chỉ gọi callback khi giá trị thực sự thay đổi
            val selectedAgeStr = selectedAge.toString()
            if (selectedAgeStr != selectedValue) {
                onValueChange(selectedAgeStr)
            }
        }
    )
}

@OptIn(ExperimentalSnapperApi::class)
@Composable
fun WheelPicker(
    modifier: Modifier,
    pickerMaxHeight: Dp = 250.dp,
    initIndex: Int? = null,
    lists: ArrayList<Int>,
    onValueSelected: (Int) -> Unit = {},
    defaultTextStyle: TextStyle = MaterialTheme.typography.bodyMedium.copy(
        fontSize = 15.sp,
    ),
    centerTextStyle: TextStyle = MaterialTheme.typography.bodyMedium.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp,
    ),
    defaultTextColor: Color = Color.Black.copy(alpha = 0.4f),
    centerTextColor: Color = Color.Black,
    selectedBackgroundColor: Color = Color.Black.copy(alpha = 0.3f)
) {
    // Lưu trạng thái cuộn và trạng thái currentIndex
    val lazyListState = rememberLazyListState(
        initialFirstVisibleItemIndex = (Int.MAX_VALUE / 2.0).toInt() - 25
    )

    // Lưu trạng thái của index đang được chọn
    var currentIndex by rememberSaveable { mutableStateOf<Int?>(null) }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(pickerMaxHeight),
            state = lazyListState,
            flingBehavior = rememberSnapperFlingBehavior(lazyListState),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            items(
                count = Int.MAX_VALUE,
                key = { it }
            ) { index ->
                // Tính toán currentIndex khi cuộn để xác định item ở giữa
                val indexInList = index % lists.size

                // Cập nhật currentIndex
                val curTextIsCenter = currentIndex == indexInList
                val curTextIsCenterDiffer =
                    currentIndex == abs(indexInList - 1) || currentIndex == abs(indexInList + 1)


                Text(
                    text = "${lists[indexInList]}",
                    style = if (curTextIsCenter) centerTextStyle else defaultTextStyle,
                    color = if (curTextIsCenter) centerTextColor else defaultTextColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(pickerMaxHeight * 0.22f)
                        .align(Alignment.Center)
                        .scale(
                            scaleX = if (curTextIsCenter) 1.2f else if (curTextIsCenterDiffer) 1.0f else 0.8f,
                            scaleY = if (curTextIsCenter) 1.2f else if (curTextIsCenterDiffer) 1.0f else 0.8f
                        )
                        .wrapContentHeight(Alignment.CenterVertically)
                )
            }
        }

        // Box hiển thị viền xung quanh picker
        Box(
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .height(pickerMaxHeight * 0.2f)
                .border(
                    width = 2.dp,
                    color = Color.Blue, // Màu viền xanh
                    shape = RoundedCornerShape(10.dp) // Bo góc cho viền
                )
        )

        // Dùng snapshotFlow để theo dõi visible items và cập nhật currentIndex
        LaunchedEffect(lazyListState) {
            snapshotFlow { lazyListState.layoutInfo.visibleItemsInfo }
                .collect { visibleItems ->
                    val centerItem = visibleItems.minByOrNull { item ->
                        val center = lazyListState.layoutInfo.viewportSize.height / 2
                        val itemCenter = item.offset + item.size / 2
                        kotlin.math.abs(itemCenter - center)
                    }
                    currentIndex = centerItem?.index?.rem(lists.size)
                }
        }

        // Flag để đảm bảo chỉ scroll 1 lần khi vào màn hình
        var hasScrolledToInit by rememberSaveable { mutableStateOf(false) }

        LaunchedEffect(initIndex, hasScrolledToInit) {
            if (initIndex != null && !hasScrolledToInit) {
                snapshotFlow { lazyListState.layoutInfo.visibleItemsInfo }
                    .filter { it.isNotEmpty() }
                    .first()

                val itemHeight = lazyListState.layoutInfo.visibleItemsInfo.first().size
                val viewportHeight = lazyListState.layoutInfo.viewportSize.height
                val centerOffset = (itemHeight - viewportHeight / 2 + itemHeight / 2)

                lazyListState.scrollToItem(
                    index = (Int.MAX_VALUE / 2.0).toInt() - 25 + initIndex,
                    scrollOffset = centerOffset
                )

                hasScrolledToInit = true
            }
        }
    }


}