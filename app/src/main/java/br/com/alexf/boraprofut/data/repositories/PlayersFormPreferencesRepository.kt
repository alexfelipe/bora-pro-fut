package br.com.alexf.boraprofut.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlayersFormPreferencesRepository(private val dataStore: DataStore<Preferences>) {

    suspend fun showGoalKeeperToolTip() {
        dataStore.edit { preferences ->
            preferences[IS_GOAL_KEEPER_TOOL_TIP_VISIBLE] = true
        }
    }

    suspend fun hideGoalKeeperToolTip() {
        dataStore.edit { preferences ->
            preferences[IS_GOAL_KEEPER_TOOL_TIP_VISIBLE] = false
        }
    }

    fun isGoalKeeperToolTipVisible(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[IS_GOAL_KEEPER_TOOL_TIP_VISIBLE] ?: true
        }
    }

    companion object {
        private val IS_GOAL_KEEPER_TOOL_TIP_VISIBLE =
            booleanPreferencesKey("isGoalkeeperToolTipVisible")
    }
}