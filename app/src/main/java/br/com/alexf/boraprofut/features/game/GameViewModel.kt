package br.com.alexf.boraprofut.features.game

import androidx.lifecycle.ViewModel
import br.com.alexf.boraprofut.data.repositories.PlayersRepository
import br.com.alexf.boraprofut.features.game.model.ReadyMadeGames
import br.com.alexf.boraprofut.features.game.model.TeamAtStandby
import br.com.alexf.boraprofut.features.game.usecase.GameUseCase
import br.com.alexf.boraprofut.features.game.usecase.ResultGames
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ReadyMadeGamesUiState(
    val readyMadeGames: List<ReadyMadeGames> = listOf(),
    val teamAtStandby: List<TeamAtStandby> = listOf(),
    val isLoading: Boolean = false
)

class GameViewModel(
    repository: PlayersRepository,
    game: GameUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReadyMadeGamesUiState())
    val uiState = _uiState.asStateFlow()

    init {
        updateState(game.getGames())
    }

    private fun updateState(fields: ResultGames) {
        val updatedData = _uiState.value.copy(
            readyMadeGames = fields.readyMadeGamesList,
            teamAtStandby = fields.teamAtStandbyList
        )
        _uiState.update { current ->
            current.copy(readyMadeGames = updatedData.readyMadeGames, teamAtStandby = updatedData.teamAtStandby)
        }
    }

}
