package com.vcole.christmas_bingo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vcole.christmas_bingo.viewmodel.BingoViewModel

@Composable
fun GameScreen(
    viewModel: BingoViewModel,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    val dummyWords = remember {
        List(25) { i -> "Item ${i + 1}" }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = onNavigateBack) { Text("Exit") }
            Text(text = uiState.currentTheme, style = MaterialTheme.typography.titleMedium)
        }

        Text(
            text = "Christmas Bingo",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // The 5x5 Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(5), // 5 columns
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f) // Makes the grid a square
                .border(2.dp, Color.Gray),
            contentPadding = PaddingValues(4.dp)
        ) {
            items(25) { index ->
                BingoCell(
                    word = dummyWords[index],
                    isMarked = uiState.selectedWords.contains(index),
                    onCellClick = { viewModel.toggleCell(index) }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (uiState.isGameOver) {
            Text("🎉 BINGO! 🎉", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.Red)
        }
    }
}

@Composable
fun BingoCell(
    word: String,
    isMarked: Boolean,
    onCellClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(2.dp)
            .aspectRatio(1f) // Square cells
            .clickable { onCellClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isMarked) Color(0xFF2E7D32) else MaterialTheme.colorScheme.surfaceVariant,
            contentColor = if (isMarked) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = word,
                fontSize = 10.sp,
                textAlign = TextAlign.Center,
                lineHeight = 12.sp,
                modifier = Modifier.padding(2.dp)
            )
        }
    }
}