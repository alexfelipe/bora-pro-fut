package br.com.alexf.boraprofut.features.players

import androidx.lifecycle.ViewModel
import br.com.alexf.boraprofut.data.repositories.PlayersRepository
import br.com.alexf.boraprofut.features.players.model.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update

data class PlayersUiState(
    val players: String = "",
    val onPlayersChange: (String) -> Unit = {},
    val isPlayersSaved: Boolean = false,
    val isSaving: Boolean = false,
)

class PlayersViewModel(
    private val repository: PlayersRepository
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
            )
        }
    }

    fun savePlayers() {
        with(_uiState.value) {
            if (isSaving) {
                return
            }
            _uiState.update {
                copy(isSaving = true)
            }
            players.parseToPlayers().let {
                repository.save(it)
            }
            _uiState.update {
                it.copy(
                    isSaving = false,
                )
            }
        }
    }

}

private fun String.parseToPlayers(): Set<Player> {
    return this.split("\n")
        .map { Player(it) }
        .toSet()
}