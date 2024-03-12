package br.com.alexf.boraprofut.features.randomteams

import br.com.alexf.boraprofut.MainDispatcherRule
import br.com.alexf.boraprofut.data.repositories.PlayersRepository
import br.com.alexf.boraprofut.features.drawTeams.useCases.TeamDrawerUseCase
import br.com.alexf.boraprofut.models.Player
import br.com.alexf.boraprofut.models.Team
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RandomTeamsViewModelTest {

    val repository: PlayersRepository = mockk()
    val useCase: TeamDrawerUseCase = mockk()

    private lateinit var viewModel: RandomTeamsViewModel

    val listPlayer = listOf(
        Player("teste1", 1),
        Player("teste2", 2),
        Player("teste3", 3),
    )

    private var repositoryPlayersMock = flow { emit(listPlayer) }


    val userCaseDrawRandomTeamsMock = listOf(
        setOf(
            Player("teste1", 1),
            Player("teste2", 2),
            Player("teste3", 3),
        )
    )


    private var repositoryPlayersPerTeamMock: Flow<Int> = flow {
        emit(5)
    }

    @Before
    fun setup() {
        prepareScenario()
    }

    @Test
    fun `testing drawTeams - after click the team should to be shuffled again`() = runTest {

        val listAfterClick = listOf(
            setOf(
                Player("teste2", 2),
                Player("teste1", 1),
                Player("teste3", 3),
            )
        )

        every {
            useCase.drawRandomTeams(any(), any())
        } returns listAfterClick

        viewModel.drawTeams()

        val stateOfViewModel: List<Team> = viewModel.uiState.value.teams
        val expect: List<Team> = listAfterClick.map { Team(it) }

        val stepOne = expect.first().players.size == stateOfViewModel.first().players.size && expect.first().players.toSet() == stateOfViewModel.first().players.toSet()

        org.junit.Assert.assertTrue(stepOne)

        var isEquals = false

        for (i in expect.first().players.indices) {
            val set1 = expect.first().players.toList()[i]
            val set2 = stateOfViewModel.first().players.toList()[i]
            if (set1 != set2) {
                isEquals = true
                break
            }
        }
        org.junit.Assert.assertTrue(isEquals)
    }


    private fun prepareScenario() {

        val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
        Dispatchers.setMain(testDispatcher)

        every {
            repository.players
        } returns repositoryPlayersMock

        every {
            repository.playersPerTeam
        } returns repositoryPlayersPerTeamMock

        every {
            useCase.drawRandomTeams(any(), any())
        } returns userCaseDrawRandomTeamsMock



        viewModel = RandomTeamsViewModel(repository, useCase)
    }

}