package br.com.alexf.boraprofut.data.repositories

import br.com.alexf.boraprofut.data.database.dao.PlayersDao
import br.com.alexf.boraprofut.data.database.entities.PlayerEntity
import br.com.alexf.boraprofut.features.game.model.Team
import br.com.alexf.boraprofut.models.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

//TODO analisar e refatorar para padronizar onde será armazenado o valor padrão
private const val defaultPlayersPerTeam = 5

class PlayersRepository(
    private val dao: PlayersDao
) {

    val players = dao.findAll()
    val games = _game.asStateFlow()
    val playersPerTeam = _playersPerTeam.asStateFlow()

    suspend fun save(players: Set<Player>) {
        val entities = players.map {
            it.toPlayerEntity()
        }
        dao.save(*entities.toTypedArray())
    }

    fun increasePlayersPerTeam() {
        _playersPerTeam.update {
            it + 1
        }
    }

    fun decreasePlayersPerTeam() {
        _playersPerTeam.update {
            if (it > 2) {
                it - 1
            } else {
                it
            }
        }
    }

    suspend fun increasePlayerLevel(player: Player) {
        if (player.level < 10) {
            val entity = player.copy(level = player.level + 1)
                .toPlayerEntity()
            dao.save(entity)
        }
    }

    suspend fun decreasePlayerLevel(player: Player) {
        if (player.level > 2) {
            val entity = player.copy(level = player.level - 1)
                .toPlayerEntity()
            dao.save(entity)
        }
    }

    fun saveGame(players: Set<Team>) {
        _game.update {
            players
                .filter {
                    it.name.trim().isNotBlank()
                }.toSet()
        }
    }

    private companion object {
        private val _game = MutableStateFlow<Set<Team>>(emptySet())
        private val _playersPerTeam = MutableStateFlow(defaultPlayersPerTeam)
    }

}

private fun Player.toPlayerEntity(): PlayerEntity {
    return PlayerEntity(
        name = this.name,
        level = this.level
    )
}
