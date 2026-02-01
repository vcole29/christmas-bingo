package com.vcole.christmas_bingo.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bingoapp.navigation.BingoScreen
import com.vcole.christmas_bingo.ui.HomeScreen
import com.vcole.christmas_bingo.ui.GameScreen
import com.vcole.christmas_bingo.ui.SettingsScreen
import com.vcole.christmas_bingo.viewmodel.BingoViewModel

@Composable
fun BingoNavGraph(viewModel: BingoViewModel = viewModel()) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = BingoScreen.Home.route) {
        composable(BingoScreen.Home.route) {
            HomeScreen(viewModel,
                onNavigateToGame = { navController.navigate(BingoScreen.Game.route) },
                onNavigateToSettings = { navController.navigate(BingoScreen.Settings.route) }
            )
        }
        composable(BingoScreen.Game.route) {
            GameScreen(viewModel, onNavigateBack = { navController.popBackStack() })
        }
        composable(BingoScreen.Settings.route) {
            SettingsScreen(viewModel, onNavigateBack = { navController.popBackStack() })
        }
    }
}