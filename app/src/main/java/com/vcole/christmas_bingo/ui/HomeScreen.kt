package com.vcole.christmas_bingo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vcole.christmas_bingo.viewmodel.BingoViewModel

@Composable
fun HomeScreen(
    viewModel: BingoViewModel,
    onNavigateToGame: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    // Connect to the ViewModel state
    val uiState by viewModel.uiState.collectAsState()
    val themes = listOf("Traditional", "Modern", "Kids")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "🎄", fontSize = 60.sp)
        Text(
            text = "Christmas Bingo",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "Choose Your Theme:", style = MaterialTheme.typography.labelLarge)

        // Theme Selection Card
        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
        ) {
            Column(Modifier.padding(8.dp)) {
                themes.forEach { text ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .selectable(
                                selected = (text == uiState.currentTheme),
                                onClick = { viewModel.updateTheme(text) },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (text == uiState.currentTheme),
                            onClick = null // null because the Row handles the click
                        )
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onNavigateToGame,
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("START GAME", fontSize = 18.sp)
        }

        TextButton(onClick = onNavigateToSettings) {
            Text("Settings")
        }
    }
}