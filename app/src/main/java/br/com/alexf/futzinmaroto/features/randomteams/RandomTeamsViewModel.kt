package br.com.alexf.futzinmaroto.features.randomteams

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alexf.futzinmaroto.data.repositories.PlayersRepository
import br.com.alexf.futzinmaroto.features.drawTeams.useCases.TeamDrawerUseCase
import br.com.alexf.futzinmaroto.models.Team
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
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
                repository.players
                    .map {
                         it.toSet()
                    },
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

    fun drawTeams() {
        viewModelScope.launch {
            val players = repository.players.first().toSet()
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