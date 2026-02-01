package com.vcole.christmas_bingo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bingoapp.navigation.BingoScreen
import com.vcole.christmas_bingo.ui.HomeScreen
import com.vcole.christmas_bingo.ui.GameScreen
import com.vcole.christmas_bingo.ui.SettingsScreen

@Composable
fun BingoNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = BingoScreen.Home.route
    ) {
        // Home Screen Logic
        composable(BingoScreen.Home.route) {
            HomeScreen(
                onNavigateToGame = { navController.navigate(BingoScreen.Game.route) },
                onNavigateToSettings = { navController.navigate(BingoScreen.Settings.route) }
            )
        }

        // Game Screen Logic
        composable(BingoScreen.Game.route) {
            GameScreen(onNavigateBack = { navController.popBackStack() })
        }

        // Settings Screen Logic
        composable(BingoScreen.Settings.route) {
            SettingsScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}