package br.com.alexf.boraprofut.features.playersForm

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.navigation.koinNavViewModel

const val playersFormRoute = "playersForm"

fun NavGraphBuilder.playersForm(
    onNavigateToDrawScreen: () -> Unit
) {
    composable(playersFormRoute) {
        val viewModel = koinNavViewModel<PlayersFormViewModel>()
        val uiState by viewModel.uiState.collectAsState(initial = PlayersUiState())
        LaunchedEffect(Unit) {
            viewModel.isPlayersSaved.collectLatest {
                onNavigateToDrawScreen()
            }
        }
        PlayersFormScreen(
            uiState = uiState,
            onSavePlayers = { viewModel.savePlayers() },
            onClearPlayers = { viewModel.clearPlayers() }
        )
    }
}

fun NavHostController.navigateToPlayersFormScreen(
    navOptions: NavOptions? = null
) {
    navigate(playersFormRoute, navOptions)
}