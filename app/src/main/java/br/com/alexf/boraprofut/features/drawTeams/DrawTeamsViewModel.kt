package br.com.alexf.boraprofut.features.drawTeams

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alexf.boraprofut.data.repositories.PlayersRepository
import br.com.alexf.boraprofut.models.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class InitState {
    data object LOADING : InitState()
    data object FINISHED : InitState()
}

data class DrawTeamsUiState(
    val players: Set<Player> = emptySet(),
    val playersPerTeam: Int = 0,
    val isShowPlayers: Boolean = true,
    val onShowPlayersToggle: () -> Unit = {},
    val onDecreasePlayersPerTeam: () -> Unit = {},
    val onIncreasePlayersPerTeam: () -> Unit = {},
    val onIncreasePlayerLevel: (Player) -> Unit = {},
    val onDecreasePlayerLevel: (Player) -> Unit = {},
    val onGoalKeeperChange: (Player) -> Unit = {},
    val initState: InitState = InitState.LOADING
)

class DrawTeamsViewModel(
    private val repository: PlayersRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DrawTeamsUiState())
    val uiState = _uiState.combine(repository.players.map {
        it.toSet()
    }) { uiState, players ->
        uiState.copy(players = players)
    }.combine(repository.playersPerTeam) { uiState, playersPerTeam ->
        uiState.copy(
            playersPerTeam = playersPerTeam,
            initState = InitState.FINISHED
        )
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
                    viewModelScope.launch {
                        repository.decreasePlayersPerTeam()
                    }
                },
                onIncreasePlayersPerTeam = {
                    viewModelScope.launch {
                        repository.increasePlayersPerTeam()
                    }
                },
                onDecreasePlayerLevel = {
                    viewModelScope.launch {
                        repository.decreasePlayerLevel(it)
                    }
                },
                onIncreasePlayerLevel = {
                    viewModelScope.launch {
                        repository.increasePlayerLevel(it)
                    }
                },
                onGoalKeeperChange = {
                    val player = it.copy(isGoalKeeper = !it.isGoalKeeper)
                    viewModelScope.launch {
                        repository.save(setOf(player))
                    }
                }
            )
        }
    }

}
