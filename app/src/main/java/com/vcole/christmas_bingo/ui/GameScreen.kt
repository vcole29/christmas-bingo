package com.vcole.christmas_bingo.ui

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
import com.vcole.christmas_bingo.viewmodel.BingoUIState

@Composable
fun GameScreen(
    viewModel: BingoViewModel,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val realWords = viewModel.gameWords

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
            Button(
                onClick = onNavigateBack,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (uiState.isHighContrast) Color.Black else MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Exit", color = Color.White)
            }
            Text(
                text = uiState.currentTheme,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = "Christmas Bingo",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 16.dp),
            color = if (uiState.isHighContrast) Color.Black else MaterialTheme.colorScheme.onBackground
        )

        if (realWords.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = if (uiState.isHighContrast) Color.Black else Color(0xFF2E7D32)
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(5),
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .border(2.dp, if (uiState.isHighContrast) Color.Black else Color.Gray),
                contentPadding = PaddingValues(4.dp)
            ) {
                items(25) { index ->
                    val wordDisplay = if (index < realWords.size) realWords[index] else ""

                    BingoCell(
                        word = wordDisplay,
                        isMarked = uiState.selectedWords.contains(index),
                        uiState = uiState, // Pass state for contrast check
                        onCellClick = { viewModel.toggleCell(index) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (uiState.isGameOver) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "🎊 BINGO! 🎊",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = if (uiState.isHighContrast) Color.Black else Color(0xFFD32F2F)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { viewModel.resetGame() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (uiState.isHighContrast) Color.Black else MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Play Again", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun BingoCell(
    word: String,
    isMarked: Boolean,
    uiState: BingoUIState,
    onCellClick: () -> Unit
) {
    // Logic for accessibility colors
    val cellColor = when {
        isMarked && uiState.isHighContrast -> Color.Yellow
        isMarked -> Color(0xFF2E7D32) // Christmas Green
        uiState.isHighContrast -> Color.White
        else -> MaterialTheme.colorScheme.surfaceVariant
    }

    val textColor = when {
        uiState.isHighContrast -> Color.Black
        isMarked -> Color.White
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    Card(
        modifier = Modifier
            .padding(2.dp)
            .aspectRatio(1f)
            .clickable { onCellClick() }
            .then(if (uiState.isHighContrast) Modifier.border(1.dp, Color.Black) else Modifier),
        colors = CardDefaults.cardColors(
            containerColor = cellColor,
            contentColor = textColor
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = word,
                fontSize = 10.sp,
                fontWeight = if (uiState.isHighContrast) FontWeight.ExtraBold else FontWeight.Medium,
                textAlign = TextAlign.Center,
                lineHeight = 12.sp,
                modifier = Modifier.padding(2.dp)
            )
        }
    }
}