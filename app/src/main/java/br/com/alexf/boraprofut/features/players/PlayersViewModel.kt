package br.com.alexf.boraprofut.features.players

import androidx.lifecycle.ViewModel
import br.com.alexf.boraprofut.features.players.model.Player
import br.com.alexf.boraprofut.features.players.useCases.TeamDrawerUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

//TODO adicionar esse valor em um meio persistente para que o usuário tenha a sua preferência
private const val defaultPlayersPerTeam: Int = 4

data class PlayersUiState(
    val players: String = "",
    val teams: Int = 2,
    val playersPerTeam: Int = defaultPlayersPerTeam,
    val drawedTeams: List<List<Player>> = emptyList(),
    val onPlayersChange: (String) -> Unit = {},
    val onIncreasesPlayersPerTeam: () -> Unit = {},
    val onDecreasesPlayersPerTeam: () -> Unit = {}
)

class PlayersViewModel(
    private val teamDrawerUseCase: TeamDrawerUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlayersUiState())

    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onPlayersChange = { players ->
                    _uiState.update {
                        it.copy(players = players)
                    }
                },
                onIncreasesPlayersPerTeam = {
                    _uiState.update {
                        it.copy(playersPerTeam = it.playersPerTeam + 1)
                    }
                },
                onDecreasesPlayersPerTeam = {
                    _uiState.update {
                        val playersPerTeam = it.playersPerTeam.let { players ->
                            if (players > 2) players - 1 else players
                        }
                        it.copy(playersPerTeam = playersPerTeam)
                    }
                }
            )
        }
    }

    fun drawTeams() {
        val playersPerTeam = _uiState.value.playersPerTeam
        val players = _uiState.value.players.parseToPlayers()
        _uiState.update {
            it.copy(
                drawedTeams = teamDrawerUseCase.drawTeams(players, playersPerTeam)
            )
        }
    }

}


private fun String.parseToPlayers(): Set<Player> {
    return this.split("\n")
        .map { Player(it) }
        .toSet()
}