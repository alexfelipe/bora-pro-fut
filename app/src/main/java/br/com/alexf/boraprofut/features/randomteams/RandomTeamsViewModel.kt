package br.com.alexf.boraprofut.features.randomteams

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alexf.boraprofut.data.repositories.PlayersRepository
import br.com.alexf.boraprofut.features.players.useCases.TeamDrawerUseCase
import br.com.alexf.boraprofut.models.Team
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RandomTeamsUiState(
    val teams: List<Team> = emptyList(),
    val playersPerTeam: Int = 0,
)

class RandomTeamsViewModel(
    private val repository: PlayersRepository,
    private val useCase: TeamDrawerUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RandomTeamsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                repository.players,
                repository.playersPerTeam
            ) { players, playersPerTeam ->
                Pair(useCase.drawRandomTeams(players, playersPerTeam), playersPerTeam)
            }.collectLatest {
                val drawnTeams = it.first.map { players ->
                    Team(players)
                }
                val playersPerTeam = it.second
                _uiState.update { currentState ->
                    currentState
                        .copy(
                            teams = drawnTeams,
                            playersPerTeam = playersPerTeam
                        )
                }
            }
        }
    }

    fun save() {
        repository.saveGame(TODO("fazer o state oferece o dado"))
    }

    fun drawTeams() {
        viewModelScope.launch {
            val players = repository.players.value
            val playersPerTeam = uiState.value.playersPerTeam
            _uiState.update { state ->
                state.copy(
                    teams = useCase.drawRandomTeams(
                        players,
                        playersPerTeam
                    ).map {
                        Team(it)
                    }
                )
            }
        }
    }

}