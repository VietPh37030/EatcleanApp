package com.example.eatclean.navigations

import com.example.eatclean.R // Đảm bảo bạn đã import package chứa tài nguyên R

sealed class BottomNavItem(val route: String, val title: String, val icon: Int) {
    object Capture : BottomNavItem("doctor", "Bác sĩ Ai", R.drawable.doctor)
    object Diet : BottomNavItem("diet", "Chế độ ăn", R.drawable.home)
    object Track : BottomNavItem("track", "Theo dõi", R.drawable.menu1)
    object Record : BottomNavItem("record", "Ghi lại", R.drawable.record)
    object Profile : BottomNavItem("profile", "Tài khoản", R.drawable.profile)
}