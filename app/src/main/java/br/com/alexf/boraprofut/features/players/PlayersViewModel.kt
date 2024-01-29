package br.com.alexf.boraprofut.features.players

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alexf.boraprofut.data.repositories.PlayersRepository
import br.com.alexf.boraprofut.models.Player
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PlayersUiState(
    val players: String = "",
    val onPlayersChange: (String) -> Unit = {},
    val isSaving: Boolean = false,
)

class PlayersViewModel(
    private val repository: PlayersRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlayersUiState())
    val uiState = _uiState.asStateFlow()
    private val _isPlayersSaved = MutableSharedFlow<Boolean>()
    val isPlayersSaved = _isPlayersSaved.asSharedFlow()

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

        viewModelScope.launch {
            repository.players.collectLatest { players ->
                _uiState.update { currentState ->
                    currentState.copy(
                        players = players
                            .joinToString("") {
                                "${it.name}\n"
                            })
                }
            }
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
                viewModelScope.launch {
                    repository.deleteAllPlayers()
                    repository.save(it)
                }
            }
            _uiState.update {
                it.copy(
                    isSaving = false,
                )
            }
            viewModelScope.launch {
                _isPlayersSaved.emit(true)
            }
        }
    }

}

private fun String.parseToPlayers(): Set<Player> {
    return this.split("\n")
        .map { Player(it) }
        .toSet()
}