package com.vcole.christmas_bingo.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(onNavigateToGame: () -> Unit, onNavigateToSettings: () -> Unit) {
    Column(
        modifier = Modifier.Companion.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Companion.CenterHorizontally
    ) {
        Text(text = "🎄 Christmas Bingo 🎄", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.Companion.height(20.dp))
        Button(onClick = onNavigateToGame) {
            Text("Start Game")
        }
        TextButton(onClick = onNavigateToSettings) {
            Text("Settings")
        }
    }
}