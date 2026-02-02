package com.vcole.christmas_bingo.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BingoDao {

    // THIS IS THE MISSING PIECE
    @Query("SELECT COUNT(*) FROM bingo_words")
    suspend fun getCount(): Int

    @Query("SELECT * FROM bingo_words WHERE theme = :themeName ORDER BY RANDOM() LIMIT 25")
    suspend fun getWordsByTheme(themeName: String): List<BingoWord>

    @Insert
    suspend fun insertAll(words: List<BingoWord>)
}