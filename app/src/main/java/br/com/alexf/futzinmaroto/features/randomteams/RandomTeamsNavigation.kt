package br.com.alexf.futzinmaroto.features.randomteams

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import org.koin.androidx.compose.koinViewModel

const val randomTeams = "randomTeams"

fun NavGraphBuilder.randomTeams() {
    composable(randomTeams) {
        val viewModel = koinViewModel<RandomTeamsViewModel>()
        val uiState by viewModel.uiState.collectAsState(initial = RandomTeamsUiState())
        RandomTeamsScreen(
            uiState,
            onDrawTeamsAgain = {
                viewModel.drawTeams()
            }
        )
    }
}

fun NavHostController.navigateToRandomTeams() {
    navigate(randomTeams)
}
