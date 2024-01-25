package br.com.alexf.boraprofut.features.game.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.alexf.boraprofut.features.game.GameScreen
import br.com.alexf.boraprofut.features.randomteams.GameViewModel
import org.koin.androidx.compose.koinViewModel

const val game = "game"

fun NavGraphBuilder.gameScreen() {
    composable(game) {
        val viewModel = koinViewModel<GameViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        GameScreen(uiState = uiState)
    }
}

fun NavHostController.navigateToGameScreen() {
    navigate(game)
}
