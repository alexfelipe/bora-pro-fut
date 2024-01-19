package br.com.alexf.boraprofut.features.players

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.androidx.compose.koinViewModel

const val playersRoute = "players"

fun NavGraphBuilder.playersScreen(
    onNavigateToDrawScreen: () -> Unit
) {
    composable(playersRoute) {
        val viewModel = koinViewModel<PlayersViewModel>()
        val uiState by viewModel.uiState
            .collectAsState(initial = PlayersUiState())
        LaunchedEffect(uiState.isPlayersSaved) {
            if (uiState.isPlayersSaved) {
                //TODO preciso investir esse comportamento,
                // pois ele está mantendo o estado ao fazer a navegação de volta
                viewModel.clearIsPlayersSaved()
                onNavigateToDrawScreen()
            }
        }
        PlayersScreen(uiState,
            onSavePlayers = {
                viewModel.savePlayers()
            }
        )
    }
}