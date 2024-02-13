package br.com.alexf.futzinmaroto.features.playersForm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alexf.futzinmaroto.data.repositories.PlayersRepository
import br.com.alexf.futzinmaroto.models.Player
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val MINIMUM_PLAYERS = 3

data class PlayersUiState(
    val players: String = "",
    val amountPlayers: Int? = null,
    val duplicateNames: String = "",
    val onPlayersChange: (String) -> Unit = {},
    val isSaving: Boolean = false,
) {
    fun isShowSaveButton() = players.isNotBlank()
            && amountPlayers != null && amountPlayers > 0
            && amountPlayers > MINIMUM_PLAYERS
}

class PlayersFormViewModel(
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
                        it.copy(
                            players = players,
                            amountPlayers = isTherePlayer(players),
                            duplicateNames = players.duplicateNames()
                                .joinToString()
                        )
                    }
                },
            )
        }

        viewModelScope.launch {
            repository.players.collectLatest { players ->
                _uiState.update { currentState ->
                    currentState.copy(
                        players = players.joinToString("") { "${it.name}\n" },
                        amountPlayers = players.size
                    )
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
            players.parseToUniquePlayers().let {
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

    fun clearPlayers() {
        _uiState.update {
            _uiState.value.copy(players = "", duplicateNames = "")
        }
        viewModelScope.launch {
            repository.deleteAllPlayers()
        }
    }

}

fun String.parseToUniquePlayers(): Set<Player> {
    return this.trim()
        .split("\n")
        .map { Player(it) }
        .toSet()
}

fun String.duplicateNames(): Set<String> {
    return this.trim()
        .split("\n")
        .groupingBy { it }
        .eachCount()
        .filter { it.value > 1 }
        .keys
}

private fun isTherePlayer(player: String): Int? {
    return if (player.isNotBlank()) player.parseToUniquePlayers().size else null
}