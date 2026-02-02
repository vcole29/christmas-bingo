package com.vcole.christmas_bingo

import com.vcole.christmas_bingo.data.BingoDao
import com.vcole.christmas_bingo.viewmodel.BingoViewModel
import io.mockk.mockk
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test

class BingoViewModelTest {

    private lateinit var viewModel: BingoViewModel
    private val mockDao = mockk<BingoDao>(relaxed = true)

    @Before
    fun setup() {
        viewModel = BingoViewModel(mockDao)
    }

    @Test
    fun `completing top row triggers game over`() {
        // Act: Toggle the first 5 cells (indices 0, 1, 2, 3, 4)
        for (i in 0..4) {
            viewModel.toggleCell(i)
        }

        // Assert: isGameOver should be true
        assertTrue(viewModel.uiState.value.isGameOver)
    }

    @Test
    fun `random selection does not trigger game over`() {
        // Act: Toggle a scattered pattern
        viewModel.toggleCell(0)
        viewModel.toggleCell(6)
        viewModel.toggleCell(12)
        viewModel.toggleCell(18)
        // (Missing 24 to complete a diagonal)

        // Assert: isGameOver should be false
        assertFalse(viewModel.uiState.value.isGameOver)
    }
}