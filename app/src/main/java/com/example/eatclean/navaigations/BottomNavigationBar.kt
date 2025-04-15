package com.example.eatclean.navigations

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Capture,
        BottomNavItem.Diet,
        BottomNavItem.Track,
        BottomNavItem.Record,
        BottomNavItem.Profile
    )

    NavigationBar(
        containerColor = Color.White // Màu nền của thanh điều hướng
    ) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route ?: ""

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(painter = painterResource(id = item.icon), contentDescription = item.title) },
                label = { Text(text = item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFFFFA500), // Màu cam cho biểu tượng khi được chọn
                    selectedTextColor = Color(0xFFFFA500), // Màu cam cho văn bản khi được chọn
                    unselectedIconColor = Color.Gray, // Màu xám cho biểu tượng khi không được chọn
                    unselectedTextColor = Color.Gray, // Màu xám cho văn bản khi không được chọn
                    indicatorColor = Color.Transparent // Không hiển thị vòng tròn chỉ báo (indicator) xung quanh biểu tượng
                )
            )
        }
    }
}