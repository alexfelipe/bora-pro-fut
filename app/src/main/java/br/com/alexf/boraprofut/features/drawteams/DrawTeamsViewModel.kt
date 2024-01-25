package br.com.alexf.boraprofut.features.drawteams

import androidx.lifecycle.ViewModel
import br.com.alexf.boraprofut.data.repositories.PlayersRepository
import br.com.alexf.boraprofut.features.players.model.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update


data class DrawTeamsUiState(
    val players: Set<Player> = emptySet(),
    val playersPerTeam: Int = 0,
    val isShowPlayers: Boolean = false,
    val onShowPlayersToggle: () -> Unit = {},
    val onDecreasePlayersPerTeam: () -> Unit = {},
    val onIncreasePlayersPerTeam: () -> Unit = {},
    val onIncreasePlayerLevel: (Player) -> Unit = {},
    val onDecreasePlayerLevel: (Player) -> Unit = {},
)

class DrawTeamsViewModel(
    private val repository: PlayersRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DrawTeamsUiState())
    val uiState = _uiState.combine(repository.players) { uiState, players -> uiState.copy(players = players)
        }.combine(repository.playersPerTeam) { uiState, playersPerTeam ->
            uiState.copy(playersPerTeam = playersPerTeam)
        }

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onShowPlayersToggle = {
                    _uiState.update {
                        it.copy(isShowPlayers = !it.isShowPlayers)
                    }
                },
                onDecreasePlayersPerTeam = {
                    repository.decreasePlayersPerTeam()
                },
                onIncreasePlayersPerTeam = {
                    repository.increasePlayersPerTeam()
                },
                onDecreasePlayerLevel = {
                    repository.decreasePlayerLevel(it)
                },
                onIncreasePlayerLevel = {
                    repository.increasePlayerLevel(it)
                }
            )
        }
    }

}
