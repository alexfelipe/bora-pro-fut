package br.com.alexf.boraprofut.features.game.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import br.com.alexf.boraprofut.features.game.GameScreen
import br.com.alexf.boraprofut.features.game.GameViewModel
import org.koin.androidx.compose.koinViewModel

const val gameRoute = "game"

fun NavGraphBuilder.gameScreen() {
    composable(gameRoute) {
        val viewModel = koinViewModel<GameViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        GameScreen(uiState = uiState)
    }
}