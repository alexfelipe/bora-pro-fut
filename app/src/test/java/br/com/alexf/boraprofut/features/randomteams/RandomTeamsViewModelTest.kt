package br.com.alexf.boraprofut.features.randomteams

import br.com.alexf.boraprofut.data.repositories.PlayersRepository
import br.com.alexf.boraprofut.features.drawTeams.useCases.TeamDrawerUseCase
import br.com.alexf.boraprofut.models.Player
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class RandomTeamsViewModelTest {
    val listPlayer = listOf(
        Player("teste1", 0),
        Player("teste2", 1),
        Player("teste3", 3),
    )
    val repository: PlayersRepository = mockk()
    val useCase: TeamDrawerUseCase = mockk()
    val drawRandomMock = listOf(setOf(
        Player("teste1", 0),
        Player("teste2", 1),
        Player("teste3", 3),
    ))
    private lateinit var viewModel: RandomTeamsViewModel
    private var playerMock = flow { emit(listPlayer) }

    private var playersPerTeamMock: Flow<Int> = flow {
        emit(5)
    }

    @Before
    fun setup() {
        prepareScenario()
    }

    @Test
    fun `testing drawTeams - after click the team should to be shuffled again`() = runTest(UnconfinedTestDispatcher()) {
        viewModel.drawTeams()
        Assert.assertNotEquals("The list needs to be different", viewModel.uiState.value, drawRandomMock)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun prepareScenario() {

        every {
            repository.players
        } returns playerMock

        every {
            repository.playersPerTeam
        } returns playersPerTeamMock

        every {
            useCase.drawRandomTeams(any(), any())
        } returns drawRandomMock

        val testDispatcher = UnconfinedTestDispatcher()

        Dispatchers.setMain(testDispatcher)

        viewModel = RandomTeamsViewModel(repository, useCase)
    }

}