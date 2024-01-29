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
    val amountPlayers: String = "",
    val duplicateNames: List<Player> = listOf(),
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
                        it.copy(
                            players = players,
                            amountPlayers = isTherePlayer(players),
                            duplicateNames = findAllDuplicates(players.parseToPlayersWithDuplicates()).toList()
                        )
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

}

fun String.parseToUniquePlayers(): Set<Player> {
    return this.trim()
        .split("\n")
        .map { Player(it) }
        .toSet()
}

private fun Set<Player>.parseToString(): String {
    return map {
        "${it.name}\n"
    }.joinToString(separator = "")
}

fun String.parseToPlayersWithDuplicates(): List<Player> {
    return this.trim()
        .split("\n")
        .map { Player("${it}, ") }
        .toList()
}

private fun findAllDuplicates(array: List<Player>): Set<Player> {
    val seen: MutableSet<Player> = mutableSetOf()
    return array.filter {
        !seen.add(it)
    }.toSet()
}

private fun isTherePlayer(player: String): String {
    return if (player.isNotBlank()) player.parseToUniquePlayers().size.toString() else ""
}

