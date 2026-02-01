package com.vcole.christmas_bingo

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// This creates the DataStore instance as an extension property on Context
private val Context.dataStore by preferencesDataStore(name = "settings")

class UserPreferencesRepository(private val context: Context) {

    // Define the keys for the pieces of data we want to save
    private val HIGH_CONTRAST = booleanPreferencesKey("high_contrast")
    private val FONT_SIZE = floatPreferencesKey("font_size")
    private val ANNOUNCE_INTERVAL = intPreferencesKey("announce_interval")

    // A Flow that emits new UserPreferences whenever the DataStore changes
    val userPreferencesFlow: Flow<UserPreferences> = context.dataStore.data.map { prefs ->
        UserPreferences(
            highContrast = prefs[HIGH_CONTRAST] ?: false,
            fontSize = prefs[FONT_SIZE] ?: 14f,
            interval = prefs[ANNOUNCE_INTERVAL] ?: 5
        )
    }

    // Function to update the High Contrast setting
    suspend fun updateHighContrast(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[HIGH_CONTRAST] = enabled
        }
    }

    // Function to update the Font Size setting
    suspend fun updateFontSize(size: Float) {
        context.dataStore.edit { preferences ->
            preferences[FONT_SIZE] = size
        }
    }

    // Function to update the Announcement Interval
    suspend fun updateInterval(seconds: Int) {
        context.dataStore.edit { preferences ->
            preferences[ANNOUNCE_INTERVAL] = seconds
        }
    }
}

/**
 * Data class to represent the settings state.
 * Keeping this at the bottom of the same file for easy access.
 */
data class UserPreferences(
    val highContrast: Boolean,
    val fontSize: Float,
    val interval: Int
)