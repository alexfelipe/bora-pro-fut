package br.com.alexf.boraprofut.data.repositories

import br.com.alexf.boraprofut.features.game.model.Team
import br.com.alexf.boraprofut.features.players.model.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

//TODO analisar e refatorar para padronizar onde será armazenado o valor padrão
private const val defaultPlayersPerTeam = 4

class PlayersRepository {

    val players = _players.asStateFlow()
    val games = _game.asStateFlow()
    val playersPerTeam = _playersPerTeam.asStateFlow()

    fun save(players: Set<Player>) {
        _players.update {
            players
                .filter {
                    it.name.trim().isNotBlank()
                }.toSet()
        }
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

    fun saveGame(players: Set<Team>) {
        _game.update {
            players
                .filter {
                    it.name.trim().isNotBlank()
                }.toSet()
        }
    }

    private companion object {
        private val _players = MutableStateFlow<Set<Player>>(emptySet())
        private val _game = MutableStateFlow<Set<Team>>(emptySet())
        private val _playersPerTeam = MutableStateFlow(defaultPlayersPerTeam)
    }

}