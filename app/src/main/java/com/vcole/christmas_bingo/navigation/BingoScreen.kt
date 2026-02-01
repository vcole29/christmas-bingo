package com.example.bingoapp.navigation

sealed class BingoScreen(val route: String) {
    object Home : BingoScreen("home")
    object Game : BingoScreen("game")
    object Settings : BingoScreen("settings")
}