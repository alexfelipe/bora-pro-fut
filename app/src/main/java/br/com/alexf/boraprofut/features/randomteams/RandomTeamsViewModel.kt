package br.com.alexf.boraprofut.features.randomteams

import androidx.lifecycle.ViewModel
import br.com.alexf.boraprofut.data.repositories.PlayersRepository
import br.com.alexf.boraprofut.features.players.model.Player
import br.com.alexf.boraprofut.features.players.useCases.TeamDrawerUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

data class RandomTeamsUiState(
    val teams: List<Set<Player>> = emptyList()
)

class RandomTeamsViewModel(
    repository: PlayersRepository,
    private val useCase: TeamDrawerUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RandomTeamsUiState())
    val uiState = combine(
        _uiState,
        repository.players,
        repository.playersPerTeam
    ) { uiState, players, playersPerTeam ->
        val teamsDrawn = useCase
            .drawRandomTeams(players, playersPerTeam)
        uiState.copy(teams = teamsDrawn)
    }


}