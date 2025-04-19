package com.example.eatclean.models

data class ProgressState(
    val currentPage: Int = 0,
    val progress: Float = 0.1f,
    val age: String = "2",
    val weight: Float = 60f,
    val unit: String = "kg",
    val heightInCm: Int = 170
)