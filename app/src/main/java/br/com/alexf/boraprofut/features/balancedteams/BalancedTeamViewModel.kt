package br.com.alexf.boraprofut.features.balancedteams

import androidx.lifecycle.ViewModel
import br.com.alexf.boraprofut.data.repositories.PlayersRepository
import br.com.alexf.boraprofut.features.players.model.Player
import br.com.alexf.boraprofut.features.players.useCases.TeamDrawerUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

data class BalancedTeamUiState(
    val teams: List<Set<Player>> = emptyList()
)

class BalancedTeamViewModel(
    repository: PlayersRepository,
    private val useCase: TeamDrawerUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(BalancedTeamUiState())
    val uiState = combine(
        _uiState,
        repository.players,
        repository.playersPerTeam
    ) { uiState, players, playersPerTeam ->
        uiState.copy(
            teams = useCase
                .drawBalancedTeams(players, playersPerTeam)
        )
    }

}