package br.com.alexf.boraprofut.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import br.com.alexf.boraprofut.data.database.dao.PlayersDao
import br.com.alexf.boraprofut.data.database.entities.PlayerEntity
import br.com.alexf.boraprofut.features.game.model.Team
import br.com.alexf.boraprofut.models.Player
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

private val playersPerTeamPreference = intPreferencesKey("playersPerTeam")
private const val DEFAULT_PLAYERS_PER_TEAM = 5

class PlayersRepository(
    private val dao: PlayersDao,
    private val dataStore: DataStore<Preferences>
) {

    val players = dao.findAll()
    val games = _game.asStateFlow()
    val playersPerTeam
        get() = dataStore.data.map {
            it[playersPerTeamPreference] ?: DEFAULT_PLAYERS_PER_TEAM
        }

    suspend fun save(players: Set<Player>) {
        val entities = players
            .filterNot { it.name.isBlank() }
            .map {
                it.toPlayerEntity()
            }
        dao.save(*entities.toTypedArray())
    }

    suspend fun increasePlayersPerTeam() {
        withContext(IO) {
            dataStore.edit {
                it[playersPerTeamPreference] = playersPerTeam.first() + 1
            }
        }
    }

    suspend fun decreasePlayersPerTeam() {
        val currentPlayersPerTeam = playersPerTeam.first()
        if (currentPlayersPerTeam > 2) {
            dataStore.edit {
                it[playersPerTeamPreference] = currentPlayersPerTeam - 1
            }
        }
    }

    suspend fun increasePlayerLevel(player: Player) {
        withContext(IO) {
            if (player.level < 10) {
                val entity = player.copy(level = player.level + 1)
                    .toPlayerEntity()
                dao.save(entity)
            }
        }
    }

    suspend fun decreasePlayerLevel(player: Player) {
        if (player.level > 0) {
            val entity = player.copy(level = player.level - 1)
                .toPlayerEntity()
            dao.save(entity)
        }
    }

    suspend fun setGoalKeeper(player: Player) {
        dao.updateGoalKeeperField(player.name, true)
    }

    suspend fun setNotGoalKeeper(player: Player) {
        dao.updateGoalKeeperField(player.name, false)
    }

    suspend fun deleteAllPlayers() {
        dao.deleteAllPlayers()
    }

    private companion object {
        private val _game = MutableStateFlow<Set<Team>>(emptySet())
    }

}

private fun Player.toPlayerEntity(): PlayerEntity {
    val name = this.name.replace(Regex("\\([Gg]\\)"), "")
    val isGoalKeeper = this.name.contains(Regex("\\([Gg]\\)"))
    return PlayerEntity(
        name = name,
        level = this.level,
        isGoalKeeper = isGoalKeeper
    )
}
