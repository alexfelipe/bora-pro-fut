package br.com.alexf.boraprofut.features.drawTeams

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.koin.androidx.compose.koinViewModel

const val drawTeamsRoute = "drawTeams"

fun NavGraphBuilder.drawTeams(
    onNavigateToRandomTeams: () -> Unit,
    onNavigateToBalancedTeams: () -> Unit,
    onNavigateToPlayersFormScreen: () -> Unit
) {
    composable(drawTeamsRoute) {
        val viewModel = koinViewModel<DrawTeamsViewModel>()
        val uiState by viewModel
            .uiState.collectAsState(initial = DrawTeamsUiState())
        when (uiState.initState) {
            InitState.LOADING -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
            }

            InitState.FINISHED -> {
                if (uiState.players.isNotEmpty()) {
                    DrawTeamsScreen(
                        uiState,
                        onDrawRandomTeamsClick = onNavigateToRandomTeams,
                        onDrawBalancedTeamsClick = onNavigateToBalancedTeams,
                        onEditPlayersClick = onNavigateToPlayersFormScreen
                    )
                } else {
                    LaunchedEffect(null) {
                        onNavigateToPlayersFormScreen()
                    }
                }
            }
        }
    }
}

fun NavHostController.navigateToDrawTeams(
    navOptions: NavOptions? = null
) {
    navigate(drawTeamsRoute, navOptions)
}
