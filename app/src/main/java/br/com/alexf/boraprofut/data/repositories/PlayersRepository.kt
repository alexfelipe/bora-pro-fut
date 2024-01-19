package br.com.alexf.boraprofut.data.repositories

import br.com.alexf.boraprofut.features.players.model.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PlayersRepository {

    val players = _players.asStateFlow()

    fun save(players: Set<Player>) {
        _players.update {
            players
        }
    }

    private companion object {
        private val _players = MutableStateFlow<Set<Player>>(emptySet())
    }

}