package br.com.alexf.boraprofut.features.players

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

//TODO adicionar esse valor em um meio persistente para que o usuário tenha a sua preferência
private const val defaultPlayersPerTeam: Int = 4

data class PlayersUiState(
    val players: String = "",
    val playersPerTeam: Int = defaultPlayersPerTeam,
    val onPlayersChange: (String) -> Unit = {},
    val onIncreasesPlayersPerTeam: () -> Unit = {},
    val onDecreasesPlayersPerTeam: () -> Unit = {}
)

class PlayersViewModel : ViewModel() {

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
        //TODO implementar a lógica para sortear times
    }

}
