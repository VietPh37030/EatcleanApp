package com.example.eatclean.models

import java.time.LocalDate



data class FoodRecord(
    val id: String,
    val name: String,
    val imageUrl: String,
    val date: LocalDate
)