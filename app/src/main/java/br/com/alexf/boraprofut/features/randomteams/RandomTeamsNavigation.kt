package br.com.alexf.boraprofut.features.randomteams

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import org.koin.androidx.compose.koinViewModel

const val randomTeams = "randomTeams"

fun NavGraphBuilder.randomTeams(
    onNavigateToGameScreen: () -> Unit
){
    composable(randomTeams) {
        val viewModel = koinViewModel<RandomTeamsViewModel>()
        val uiState by viewModel.uiState.collectAsState(initial = RandomTeamsUiState())
        RandomTeamsScreen(uiState){
            viewModel.save(it)
            onNavigateToGameScreen()
        }
    }
}

fun NavHostController.navigateToRandomTeams() {
    navigate(randomTeams)
}
