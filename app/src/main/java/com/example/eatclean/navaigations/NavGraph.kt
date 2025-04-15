package com.example.eatclean

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eatclean.navigations.BottomNavigationBar
import com.example.eatclean.navigations.BottomNavItem
import com.example.eatclean.ui.screens.DietPlanScreenContainer
import com.example.eatclean.ui.screens.DietPlanScreenContent
import com.example.eatclean.ui.screens.FollowScreenContent




@Composable
fun NavGraph() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Capture.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Capture.route) {
                Text("Màn hình Bac si suc khoe")
            }
            composable(BottomNavItem.Diet.route) {
                DietPlanScreenContainer()
            }
            composable(BottomNavItem.Track.route) {
              FollowScreenContent()
            }
            composable(BottomNavItem.Record.route) {
                Text("Màn hình Ghi lại")
            }
            composable(BottomNavItem.Profile.route) {
                Text("Màn hình Tài khoản")
            }
        }
    }
}