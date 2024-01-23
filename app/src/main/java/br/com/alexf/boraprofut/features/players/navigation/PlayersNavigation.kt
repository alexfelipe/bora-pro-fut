package br.com.alexf.boraprofut.features.players.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import br.com.alexf.boraprofut.features.players.PlayersScreen
import br.com.alexf.boraprofut.features.players.PlayersViewModel
import org.koin.androidx.compose.koinViewModel

const val playersRoute = "players"

fun NavGraphBuilder.playersScreen() {
    composable(playersRoute) {
        val viewModel = koinViewModel<PlayersViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        PlayersScreen(uiState,
            onDrawTeamsClick = {
                viewModel.drawTeams()
            }
        )
    }
}