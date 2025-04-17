package com.example.eatclean.ui.theme.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.eatclean.R
import com.example.eatclean.models.ProteinFood
import com.example.eatclean.models.ProteinInfo
import com.example.eatclean.ui.theme.components.BarChart
import com.example.eatclean.ui.theme.components.BarChartView
import com.example.eatclean.ui.theme.components.InfoRow
import com.example.eatclean.viewmodels.ProteinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProteinScreen(
    navController: NavController,
    viewModel: ProteinViewModel = viewModel()
) {
    val proteinFoods by viewModel.proteinFoods.collectAsState()
    val dailyData by viewModel.dailyData.collectAsState()
    val proteinInfos by viewModel.proteinInfos.collectAsState()

    val dailyGoal = viewModel.getDailyGoal()
    val consumedToday = viewModel.getConsumedToday()
    val progress = consumedToday.toFloat() / dailyGoal

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Protein",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },

            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Header with Progress
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .shadow(8.dp, RoundedCornerShape(24.dp)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    progress = progress,
                                    modifier = Modifier.fillMaxSize(),
                                    strokeWidth = 10.dp,
                                    color = MaterialTheme.colorScheme.primary,
                                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                                )
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "$consumedToday",
                                        style = MaterialTheme.typography.displaySmall,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        text = "/${dailyGoal}g",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(24.dp))
                            Column {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Mục tiêu hàng ngày",
                                        style = MaterialTheme.typography.titleLarge.copy(
                                            fontWeight = FontWeight.Bold
                                        ),
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    IconButton(
                                        onClick = { /* Handle edit */ },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.Edit,
                                            contentDescription = "Edit",
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                                Text(
                                    text = "\"Khối Xây Dựng cho Sức Mạnh, Năng Lượng và Cuộc Sống! 💪🍗\"",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontStyle = FontStyle.Italic
                                    ),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }

            // Chart Section
            item {
                var showDropdown by remember { mutableStateOf(false) }
                var selectedPeriod by remember { mutableStateOf("7 Ngày") }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .shadow(8.dp, RoundedCornerShape(24.dp)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.06f),
                                        MaterialTheme.colorScheme.secondary.copy(alpha = 0.04f)
                                    )
                                ),
                                shape = RoundedCornerShape(24.dp)
                            )
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ltd),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(28.dp)
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    "Tiêu thụ",
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            Box {
                                TextButton(onClick = { showDropdown = true }) {
                                    Text(
                                        text = selectedPeriod,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                                DropdownMenu(
                                    expanded = showDropdown,
                                    onDismissRequest = { showDropdown = false }
                                ) {
                                    DropdownMenuItem(
                                        text = { Text("7 Ngày") },
                                        onClick = {
                                            selectedPeriod = "7 Ngày"
                                            showDropdown = false
                                            // Handle 7-day data fetch
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = { Text("14 Ngày") },
                                        onClick = {
                                            selectedPeriod = "14 Ngày"
                                            showDropdown = false
                                            // Handle 14-day data fetch
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = { Text("30 Ngày") },
                                        onClick = {
                                            selectedPeriod = "30 Ngày"
                                            showDropdown = false
                                            // Handle 30-day data fetch
                                        }
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        BarChartView(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp)
                                .padding(horizontal = 8.dp),
                            values = dailyData.map { it.consumedAmount.toFloat() },
                            labels = dailyData.map { it.date },
                            avg = dailyData.map { it.consumedAmount }.average().toFloat()
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            ChartLegendItem(
                                color = MaterialTheme.colorScheme.error,
                                text = "Mục tiêu hàng ngày"
                            )
                            ChartLegendItem(
                                color = MaterialTheme.colorScheme.primary,
                                text = "Trung bình hàng ngày"
                            )
                        }
                    }
                }
            }

            // History Section
            item {
                SectionHeader(
                    icon = R.drawable.history,
                    title = "Lịch Sử"
                )
            }

            // Food History Items
            items(proteinFoods.size) { index ->
                FoodHistoryItem(
                    food = proteinFoods[index],
                    viewModel = viewModel,
                    showDivider = index < proteinFoods.size - 1
                )
            }

            // Information Section
            items(proteinInfos.size) { index ->
                InfoItem(
                    info = proteinInfos[index],
                    index = index,
                    showDivider = index < proteinInfos.size - 1
                )
            }
        }
    }
}

@Composable
private fun ChartLegendItem(color: Color, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(color, color.copy(alpha = 0.5f)),
                        radius = 8f
                    )
                )
        )
        Spacer(Modifier.width(6.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun SectionHeader(
    @DrawableRes icon: Int,
    title: String
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.secondary // Đổi màu chữ sang secondary
            )
        }
        Divider(
            color = MaterialTheme.colorScheme.outlineVariant,
            thickness = 1.2.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
private fun FoodHistoryItem(
    food: ProteinFood,
    viewModel: ProteinViewModel,
    showDivider: Boolean
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ai),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = food.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = viewModel.getFormattedDateTime(food),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = "${food.proteinAmount}g",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }
        if (showDivider) {
            Divider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.outlineVariant
            )
        }
    }
}

@Composable
private fun InfoItem(
    info: ProteinInfo,
    index: Int,
    showDivider: Boolean
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        InfoRow(
            iconRes = R.drawable.heart,
            text = info.title
        )
        Text(
            text = info.content,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 44.dp, bottom = 8.dp, top = 8.dp, end = 8.dp),
            color = MaterialTheme.colorScheme.onSurface
        )
        if (showDivider) {
            Divider(
                modifier = Modifier.padding(horizontal = 32.dp),
                color = MaterialTheme.colorScheme.outlineVariant
            )
        }
    }
}