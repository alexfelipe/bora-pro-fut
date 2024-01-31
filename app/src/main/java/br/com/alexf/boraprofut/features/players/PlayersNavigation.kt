package br.com.alexf.boraprofut.features.players

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.androidx.compose.navigation.koinNavViewModel

const val playersRoute = "players"
fun NavGraphBuilder.playersScreen(
    onNavigateToDrawScreen: () -> Unit
) {
    composable(playersRoute) {
        val viewModel = koinNavViewModel<PlayersViewModel>()
        val uiState by viewModel.uiState.collectAsState(initial = PlayersUiState())
        PlayersScreen(
            uiState = uiState,
            onSavePlayers = { viewModel.savePlayers() },
            onClearTheField = { viewModel.clearField() })
    }
}