package com.vcole.christmas_bingo.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.vcole.christmas_bingo.data.BingoDao
import com.vcole.christmas_bingo.data.BingoWord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class BingoUIState(
    val currentTheme: String = "Traditional",
    val selectedWords: Set<Int> = emptySet(),
    val isGameOver: Boolean = false,
    val isHighContrast: Boolean = false
)

class BingoViewModel(private val dao: BingoDao) : ViewModel() {

    private val _uiState = MutableStateFlow(BingoUIState())
    val uiState: StateFlow<BingoUIState> = _uiState.asStateFlow()

    // This holds the actual words shown on the Bingo grid
    var gameWords by mutableStateOf<List<String>>(emptyList())

    fun updateTheme(newTheme: String) {
        _uiState.update { it.copy(currentTheme = newTheme) }
    }

    fun toggleHighContrast() {
        _uiState.update { it.copy(isHighContrast = !it.isHighContrast) }
    }

    /**
     * Fills the database with Christmas words if it is empty.
     */
    fun seedDatabase() {
        viewModelScope.launch {
            // Switch to IO thread for database operations
            kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
                try {
                    val count = dao.getCount()
                    if (count == 0) {
                        val allWords = mutableListOf<BingoWord>()

                        // 1. Traditional Theme
                        val trad = listOf(
                            "Manger", "Star", "Angel", "Joseph", "Mary", "Stable",
                            "Wisemen", "Frankincense", "Myrrh", "Gold", "Camel",
                            "Shepherds", "Sheep", "Donkey", "Bethlehem", "Inn",
                            "Cradle", "Hay", "Swaddling", "Peace", "Joy", "Savior",
                            "Christ", "Holy", "Silent Night"
                        )
                        trad.forEach { allWords.add(BingoWord(word = it, theme = "Traditional")) }

                        // 2. Modern Theme
                        val modern = listOf(
                            "Santa", "Elves", "Reindeer", "Sleigh", "North Pole",
                            "Presents", "Tree", "Lights", "Ornaments", "Stocking",
                            "Coal", "Cookies", "Milk", "Chimney", "Wreath",
                            "Mistletoe", "Holly", "Snowman", "Candy Cane",
                            "Gingerbread", "Eggnog", "Carols", "Grinch", "Scrooge", "Hot Cocoa"
                        )
                        modern.forEach { allWords.add(BingoWord(word = it, theme = "Modern")) }

                        // 3. Kids Theme
                        val kids = listOf(
                            "Rudolph", "Frosty", "Mrs. Claus", "Workshop", "Toys",
                            "Wrapping Paper", "Bow", "Snowball", "Icicle", "Mittens",
                            "Scarf", "Hot Chocolate", "Bell", "Drum", "Train",
                            "Puppy", "Star", "Snowflake", "Sparkle", "Gifts",
                            "Sled", "Tinsel", "Nutcracker", "Teddy Bear", "Penguin"
                        )
                        kids.forEach { allWords.add(BingoWord(word = it, theme = "Kids")) }

                        dao.insertAll(allWords)
                        android.util.Log.d("BINGO", "Database Seeded with ${allWords.size} words")
                    }
                } catch (e: Exception) {
                    android.util.Log.e("BINGO", "Seed Failed: ${e.message}")
                }
            }
        }
    }

    /**
     * Fetches 25 random words from the database based on the selected theme.
     */
    fun loadWords() {
        viewModelScope.launch(Dispatchers.IO) {
            val theme = _uiState.value.currentTheme
            val fetched = dao.getWordsByTheme(theme)

            // Use Dispatchers.Main to update the UI variable
            launch(Dispatchers.Main) {
                gameWords = fetched.map { it.word }
            }
        }
    }

    fun toggleCell(index: Int) {
        if (_uiState.value.isGameOver) return

        _uiState.update { state ->
            val newSelection = if (state.selectedWords.contains(index)) {
                state.selectedWords - index
            } else {
                state.selectedWords + index
            }

            val won = checkWin(newSelection)
            state.copy(selectedWords = newSelection, isGameOver = won)
        }
    }

    private fun checkWin(selections: Set<Int>): Boolean {
        val winPatterns = listOf(
            setOf(0, 1, 2, 3, 4), setOf(5, 6, 7, 8, 9), setOf(10, 11, 12, 13, 14),
            setOf(15, 16, 17, 18, 19), setOf(20, 21, 22, 23, 24),
            setOf(0, 5, 10, 15, 20), setOf(1, 6, 11, 16, 21), setOf(2, 7, 12, 17, 22),
            setOf(3, 8, 13, 18, 23), setOf(4, 9, 14, 19, 24),
            setOf(0, 6, 12, 18, 24), setOf(4, 8, 12, 16, 20)
        )
        return winPatterns.any { pattern -> selections.containsAll(pattern) }
    }

    fun resetGame() {
        _uiState.update { BingoUIState(currentTheme = it.currentTheme) }
        loadWords()
    }
}

class BingoViewModelFactory(private val dao: BingoDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BingoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BingoViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}