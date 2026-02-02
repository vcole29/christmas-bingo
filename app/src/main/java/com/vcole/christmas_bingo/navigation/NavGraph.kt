package com.vcole.christmas_bingo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vcole.christmas_bingo.ui.HomeScreen
import com.vcole.christmas_bingo.ui.GameScreen
import com.vcole.christmas_bingo.ui.SettingsScreen
import com.vcole.christmas_bingo.viewmodel.BingoViewModel
import com.vcole.christmas_bingo.viewmodel.BingoViewModelFactory

@Composable
fun BingoNavGraph(factory: BingoViewModelFactory) {
    val viewModel: BingoViewModel = viewModel(factory = factory)
    val navController = rememberNavController()

    // NEW: Seed the database the moment the app is opened
    LaunchedEffect(Unit) {
        viewModel.seedDatabase()
    }

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                viewModel = viewModel,
                onStartGame = {
                    // NEW: Tell the ViewModel to fetch words from DB before navigating
                    viewModel.loadWords()
                    navController.navigate("game")
                },
                onNavigateToSettings = { navController.navigate("settings") }
            )
        }
        composable("game") {
            GameScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable("settings") {
            SettingsScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}