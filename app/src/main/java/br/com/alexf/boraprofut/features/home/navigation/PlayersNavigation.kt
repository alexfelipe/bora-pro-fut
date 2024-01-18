package br.com.alexf.boraprofut.features.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import br.com.alexf.boraprofut.features.home.PlayersScreen
import br.com.alexf.boraprofut.features.home.PlayersUiState

const val playersRoute = "players"

fun NavGraphBuilder.homeScreen() {
    composable(playersRoute) {
        val uiState = PlayersUiState(
            players = "alex\nfelipe",
            onPlayersChange = {}
        )
        PlayersScreen(uiState)
    }
}