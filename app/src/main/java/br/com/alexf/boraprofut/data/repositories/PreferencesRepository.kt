package br.com.alexf.boraprofut.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesRepository(private val dataStore: DataStore<Preferences>) {

    suspend fun showToolTip() {
        dataStore.edit { preferences ->
            preferences[IS_TOOL_TIP_VISIBLE] = true
        }
    }

    suspend fun hideToolTip() {
        dataStore.edit { preferences ->
            preferences[IS_TOOL_TIP_VISIBLE] = false
        }
    }

    fun isToolTipVisible(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[IS_TOOL_TIP_VISIBLE] ?: true
        }
    }

    companion object {
        private val IS_TOOL_TIP_VISIBLE = booleanPreferencesKey("isToolTipVisible")
    }
}