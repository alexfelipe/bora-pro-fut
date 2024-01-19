package br.com.alexf.boraprofut.features.drawteams

import androidx.lifecycle.ViewModel
import br.com.alexf.boraprofut.data.repositories.PlayersRepository
import br.com.alexf.boraprofut.features.players.model.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update

//TODO refatorar para adicionar em um meio persistente
private const val defaultPlayersPerTeam = 4

data class DrawTeamsUiState(
    val players: Set<Player> = emptySet(),
    val playersPerTeam: Int = defaultPlayersPerTeam,
    val isShowPlayers: Boolean = false,
    val onShowPlayersToggle: () -> Unit = {}
)

class DrawTeamsViewModel(
    private val repository: PlayersRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DrawTeamsUiState())
    val uiState = _uiState.combine(repository.players) { uiState, players ->
        uiState.copy(players = players)
    }

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onShowPlayersToggle = {
                    _uiState.update {
                        it.copy(isShowPlayers = !it.isShowPlayers)
                    }
                }
            )
        }
    }

    fun increasePlayersPerTeam() {
        _uiState.update {
            it.copy(playersPerTeam = it.playersPerTeam + 1)
        }
    }

    fun decreasePlayersPerTeam() {
        if(_uiState.value.playersPerTeam > 2){
            _uiState.update {
                it.copy(playersPerTeam = it.playersPerTeam - 1)
            }
        }
    }

}
