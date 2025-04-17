package com.example.eatclean

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.eatclean.navigations.BottomNavigationBar
import com.example.eatclean.navigations.BottomNavItem
import com.example.eatclean.ui.screens.DietPlanScreenContainer
import com.example.eatclean.ui.screens.FollowScreenContent
import com.example.eatclean.ui.theme.screens.ExpertAiScreenContent
import com.example.eatclean.ui.theme.screens.ProteinScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route

    val routesWithBottomBar = listOf(
        BottomNavItem.Capture.route,
        BottomNavItem.Diet.route,
        BottomNavItem.Track.route,
        BottomNavItem.Record.route,
        BottomNavItem.Profile.route
    )

    val shouldShowBottomBar = currentDestination in routesWithBottomBar

    // Animate alpha
    val bottomBarAlpha by animateFloatAsState(
        targetValue = if (shouldShowBottomBar) 1f else 0f,
        label = "bottomBarAlpha"
    )
    // Animate bottom padding (height of BottomNavigationBar, ví dụ 80.dp)
    val bottomBarHeight = 80.dp
    val animatedBottomPadding by animateDpAsState(
        targetValue = if (shouldShowBottomBar) bottomBarHeight else 0.dp,
        label = "bottomBarPadding"
    )

    Scaffold(
        bottomBar = {
            if (bottomBarAlpha > 0f) {
                BottomNavigationBar(
                    navController = navController,
                    modifier = Modifier
                        .alpha(bottomBarAlpha)
                        .then(
                            if (shouldShowBottomBar) Modifier else Modifier.pointerInput(Unit) {}
                        )
                )
            }
        }
    ) { innerPadding ->
        // Điều chỉnh innerPadding: animate bottom padding
        val adjustedPadding = PaddingValues(
            start = innerPadding.calculateStartPadding(androidx.compose.ui.unit.LayoutDirection.Ltr),
            top = innerPadding.calculateTopPadding(),
            end = innerPadding.calculateEndPadding(androidx.compose.ui.unit.LayoutDirection.Ltr),
            bottom = animatedBottomPadding
        )

        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Capture.route,
            modifier = Modifier.padding(adjustedPadding)
        ) {
            composable(BottomNavItem.Capture.route) {
                ExpertAiScreenContent()
            }
            composable(BottomNavItem.Diet.route) {
                DietPlanScreenContainer()
            }
            composable(BottomNavItem.Track.route) {
                FollowScreenContent(navController = navController)
            }
            composable(BottomNavItem.Record.route) {
                Text("Màn hình Ghi lại")
            }
            composable(BottomNavItem.Profile.route) {
                Text("Màn hình Tài khoản")
            }
            composable("proteinScreen") {
                ProteinScreen(navController = navController)
            }
        }
    }
}