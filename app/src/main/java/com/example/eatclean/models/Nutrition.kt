package com.example.eatclean.models



data class Nutrition(
    val currentValue: Int,
    val targetValue: Int,
    val label: String,
    val unit: String,
    val progressColor: String // Lưu mã màu dưới dạng hex để dễ serialize
)