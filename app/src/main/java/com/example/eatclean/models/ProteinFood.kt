package com.example.eatclean.models

// D:\Project Android\Eatclean\app\src\main\java\com\example\eatclean\models\Protein.kt

import java.time.LocalDateTime

data class ProteinFood(
    val id: String,
    val name: String,
    val amount: String,
    val proteinAmount: Double,
    val consumedAt: LocalDateTime
)

data class ProteinDailyData(
    val date: String,
    val consumedAmount: Double,
    val targetAmount: Double
)

data class ProteinInfo(
    val title: String,
    val content: String
)