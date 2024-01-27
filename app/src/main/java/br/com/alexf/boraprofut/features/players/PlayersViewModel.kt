package br.com.alexf.boraprofut.features.players

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alexf.boraprofut.data.repositories.PlayersRepository
import br.com.alexf.boraprofut.models.Player
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PlayersUiState(
    val players: String = "",
    val playersList: List<Player> = listOf(),
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
        //TODO adicionado apenas para pular para a tela de sorteio diretamente [é gambiarra]
        viewModelScope.launch {
            delay(1000)
            if(repository.players.first().isNotEmpty()) {
                _isPlayersSaved.emit(true)
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
                repository.save(it.toSet())
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

fun String.parseToPlayers(duplicityEnabler: Boolean = true): List<Player> {
    return if (duplicityEnabler) {
        this.trim()
            .split("\n")
            .map { Player(it) }
            .toSet().toList()
    } else {
        this.trim()
            .split("\n")
            .map { Player(it) }
            .toList()
    }
}

private fun Set<Player>.parseToString(): String {
    return map {
        "${it.name}\n"
    }.joinToString(separator = "")
}