package com.vcole.christmas_bingo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.vcole.christmas_bingo.data.BingoDatabase
import com.vcole.christmas_bingo.navigation.BingoNavGraph
import com.vcole.christmas_bingo.ui.theme.*
import com.vcole.christmas_bingo.viewmodel.BingoViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            BingoDatabase::class.java, "bingo-db"
        ).fallbackToDestructiveMigration() // Add this to help with version changes
            .build()

        val factory = BingoViewModelFactory(db.bingoDao())

        setContent {
            ChristmasbingoTheme() {
                BingoNavGraph(factory = factory)
            }
        }
    }
}