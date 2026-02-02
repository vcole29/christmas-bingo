package com.vcole.christmas_bingo.data

import androidx.room.Database
import androidx.room.RoomDatabase

// Added exportSchema = false to prevent compile-time warnings
@Database(entities = [BingoWord::class], version = 1, exportSchema = false)
abstract class BingoDatabase : RoomDatabase() {
    abstract fun bingoDao(): BingoDao
}