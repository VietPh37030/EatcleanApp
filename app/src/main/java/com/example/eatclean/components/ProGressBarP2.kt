package com.example.eatclean.components

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
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.LazyListSnapperLayoutInfo
import dev.chrisbanes.snapper.rememberLazyListSnapperLayoutInfo
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlin.math.abs
import kotlin.math.roundToInt


//Screen Content3
@Composable
fun DaggerWheelPicker() {
    val lists = arrayListOf<Int>().apply {
        for (i in 1..100) {
            add(i)
        }
    }

    WheelPicker(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        pickerMaxHeight = 250.dp,
        lists = lists
    )
}

@OptIn(ExperimentalSnapperApi::class)
@Composable
fun WheelPicker(
    modifier: Modifier,
    pickerMaxHeight: Dp = 250.dp,
    initIndex: Int? = null,
    lists: ArrayList<Int>,
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
                val curTextIsCenterDiffer = currentIndex == abs(indexInList - 1) || currentIndex == abs(indexInList + 1)


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

        // Scroll tới index mặc định khi có initIndex
        LaunchedEffect(initIndex) {
            snapshotFlow { lazyListState.layoutInfo.visibleItemsInfo }
                .filter { it.isNotEmpty() }
                .first() // Đợi khi danh sách hiển thị có ít nhất 1 item

            val itemSize = lazyListState.layoutInfo.visibleItemsInfo.first().size

            initIndex?.let {
                lazyListState.scrollToItem(
                    index = (Int.MAX_VALUE / 2.0).toInt() - 25 + it,
                    scrollOffset = itemSize / 5
                )
            } ?: kotlin.run {
                lazyListState.scrollBy(
                    value = itemSize.toFloat() / 5
                )
            }
        }
    }
}


//Screen Content4
@Composable
fun WeightScale(
    initialWeight: Float = 77f,
    minWeight: Int = 20,
    maxWeight: Int = 100,
    primaryColor: Color = Color(0xFF4CAF50),
    secondaryColor: Color = Color(0xFF2196F3),
    onWeightChange: (Float) -> Unit
) {
    val ticksPerKg = 10 // Đơn vị chia nhỏ là 0.1kg
    val tickSpacingDp = 20.dp
    val totalTicks = (maxWeight - minWeight) * ticksPerKg + 1

    val density = LocalDensity.current
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val tickSpacingPx = with(density) { tickSpacingDp.toPx() }
    val centerOffsetPx = with(density) { screenWidth.toPx() / 2.1f }

    // Đảm bảo làm tròn ban đầu
    val roundedInitialWeight = (initialWeight * ticksPerKg).roundToInt() / ticksPerKg.toFloat()

    // Tính toán offset ban đầu sao cho đúng vạch tick
    val initialOffset =
        ((roundedInitialWeight - minWeight) * ticksPerKg * tickSpacingPx - centerOffsetPx + tickSpacingPx / 6).roundToInt()

    var currentWeight by remember { mutableStateOf(roundedInitialWeight) }

    val scrollState = rememberScrollState(initial = initialOffset)

    // Cập nhật trọng lượng từ offset cuộn, chỉ thay đổi khi đã vượt qua một vạch tick đầy đủ
    fun calculateWeightFromScroll(scroll: Int): Float {
        val raw =
            minWeight + (scroll + centerOffsetPx + tickSpacingPx / 6) / tickSpacingPx / ticksPerKg
        return (raw * ticksPerKg).toInt() / ticksPerKg.toFloat()
    }

    // Cập nhật vạch chỉ khi có sự thay đổi đáng kể
    LaunchedEffect(scrollState.value) {
        val weight = calculateWeightFromScroll(scrollState.value)

        // Nếu trọng lượng đã thay đổi và vượt qua ngưỡng, cập nhật
        if (weight != currentWeight) {
            currentWeight = weight.coerceIn(minWeight.toFloat(), maxWeight.toFloat())
            onWeightChange(currentWeight)
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        Text(
            text = String.format("%.1f kg", currentWeight),
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold,
            color = primaryColor,
            modifier = Modifier.padding(10.dp)
        )

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
        ) {
            Row(
                modifier = Modifier
                    .horizontalScroll(scrollState)
                    .width(tickSpacingDp * totalTicks)
                    .fillMaxHeight(),
                verticalAlignment = Alignment.Bottom
            ) {
                for (i in 0 until totalTicks) {
                    val tickWeight = minWeight + i.toFloat() / ticksPerKg
                    val isMajor = tickWeight % 1f == 0f
                    val isHalf = tickWeight % 0.5f == 0f
                    val selected = tickWeight == currentWeight

                    Column(
                        modifier = Modifier
                            .width(tickSpacingDp)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Bottom, // ✅ Căn đáy
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (isMajor && !selected) {
                            Text(
                                text = "${tickWeight.roundToInt()}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.DarkGray
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Box(
                            modifier = Modifier
                                .width(2.dp)
                                .height(
                                    when {
                                        isMajor -> 40.dp
                                        isHalf -> 30.dp
                                        else -> 20.dp
                                    }
                                )
                                .background(Color.Black)
                        )
                    }
                }
            }

            // Vạch chỉ màu
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .width(4.dp)
                    .fillMaxHeight()
                    .background(secondaryColor)
            )
        }
    }
}

@Composable
fun BMIIndicatorBar(
    bmi: Float,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(60.dp)
        .padding(10.dp)
) {
    val colors = listOf(
        Color(0xFF42A5F5), // Gầy
        Color(0xFF66BB6A), // Bình thường
        Color(0xFFFFA726), // Thừa cân
        Color(0xFFEF5350)  // Béo phì
    )
    val labels = listOf("Gầy", "Bình thường", "Thừa cân", "Béo phì")
    val bmiRanges = listOf(0f, 18.5f, 25f, 30f, 40f)

    BoxWithConstraints(modifier = modifier) {
        val fullWidthPx = constraints.maxWidth.toFloat()

        // Tính vị trí vạch chỉ BMI theo UI chia đều
        val uiRegions = listOf(0f to 18.5f, 18.5f to 25f, 25f to 30f, 30f to 40f)
        val regionWidthFraction = 1f / uiRegions.size

        val (regionIndex, regionStart, regionEnd) = uiRegions.withIndex().firstOrNull { (_, range) ->
            bmi in range.first..range.second
        }?.let { (i, range) ->
            Triple(i, range.first, range.second)
        } ?: Triple(0, 0f, 18.5f)

        val bmiInRegionFraction = ((bmi - regionStart) / (regionEnd - regionStart)).coerceIn(0f, 1f)
        val bmiUIPositionFraction = regionIndex * regionWidthFraction + bmiInRegionFraction * regionWidthFraction
        val bmiPositionPx = fullWidthPx * bmiUIPositionFraction

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // --- Mốc BMI trên giao diện (chia đều theo UI) ---
            Box(modifier = Modifier.fillMaxWidth()) {
                val displayBMIs = listOf("18.5", "25.0", "30.0")
                val positions = listOf(0.25f, 0.5f, 0.75f) // UI offset chia đều

                positions.forEachIndexed { index, pos ->
                    val offset = (fullWidthPx * pos).toInt()
                    Text(
                        text = displayBMIs[index],
                        fontSize = 10.sp,
                        color = Color.Gray,
                        modifier = Modifier
                            .offset { IntOffset(offset - 10.dp.toPx().toInt(), 0) }
                    )
                }
            }


            // --- Thanh màu và vạch ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .clip(RoundedCornerShape(6.dp))
            ) {
                Row(Modifier.fillMaxSize()) {
                    colors.forEach { color ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .background(color)
                        )
                    }
                }

                // --- Vạch chỉ BMI ---
                Box(
                    modifier = Modifier
                        .offset { IntOffset(x = (bmiPositionPx - 1.dp.toPx()).toInt().coerceAtMost((fullWidthPx - 2.dp.toPx()).toInt()), y = 0) }
                        .width(2.dp)
                        .fillMaxHeight()
                        .background(Color.Black)
                )
            }

            // --- Label bên dưới ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                labels.forEach { label ->
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        Text(
                            text = label,
                            fontSize = 10.sp,
                            color = Color.DarkGray
                        )
                    }
                }
            }
        }
    }
}