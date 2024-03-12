package br.com.alexf.boraprofut.features.playersForm

import br.com.alexf.boraprofut.data.repositories.PlayersRepository
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before


class PlayersFormViewModelTest{
    private lateinit var viewModel: PlayersFormViewModel
    val repository: PlayersRepository = mockk()
    @Before
    fun setup() {
        prepareScenario()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun prepareScenario() {

        val testDispatcher = UnconfinedTestDispatcher()
        Dispatchers.setMain(testDispatcher)

        viewModel = PlayersFormViewModel(repository)
    }

}