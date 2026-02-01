package com.vcole.christmas_bingo.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class BingoUIState(
    val currentTheme: String = "Traditional",
    val selectedWords: Set<Int> = emptySet(), // Stores the index of clicked cells
    val isGameOver: Boolean = false
)

class BingoViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(BingoUIState())
    val uiState: StateFlow<BingoUIState> = _uiState.asStateFlow()

    fun updateTheme(newTheme: String) {
        _uiState.update { it.copy(currentTheme = newTheme) }
    }

    fun toggleCell(index: Int) {
        _uiState.update { state ->
            val newSelection = if (state.selectedWords.contains(index)) {
                state.selectedWords - index
            } else {
                state.selectedWords + index
            }
            state.copy(selectedWords = newSelection)
        }
    }
}