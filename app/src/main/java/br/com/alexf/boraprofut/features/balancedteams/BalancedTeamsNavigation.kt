package br.com.alexf.boraprofut.features.balancedteams

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import org.koin.androidx.compose.koinViewModel

const val balancedTeamsRoute = "balancedTeams"

fun NavGraphBuilder.balancedTeams(){
    composable(balancedTeamsRoute) {
        val viewModel = koinViewModel<BalancedTeamViewModel>()
        val uiState by viewModel.uiState
            .collectAsState(initial = BalancedTeamUiState())
        BalancedTeamsScreen(uiState = uiState)
    }
}

fun NavHostController.navigateToBalancedTeams(){
    navigate(balancedTeamsRoute)
}