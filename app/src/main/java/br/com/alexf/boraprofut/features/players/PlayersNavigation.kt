package br.com.alexf.boraprofut.features.players

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import br.com.alexf.boraprofut.features.topbar.ScaffoldTopBar
import org.koin.androidx.compose.navigation.koinNavViewModel

const val playersRoute = "players"
fun NavGraphBuilder.playersScreen(
    onNavigateToDrawScreen: () -> Unit
) {
    composable(playersRoute) {
        val viewModel = koinNavViewModel<PlayersViewModel>()
        val uiState by viewModel.uiState.collectAsState(initial = PlayersUiState())
        Scaffold(topBar = { ScaffoldTopBar(modifier = Modifier.fillMaxWidth()) }) {
            PlayersScreen(
                modifier = Modifier.padding(it),
                uiState = uiState,
                onSavePlayers = { viewModel.savePlayers() },
                onClearTheField = { viewModel.clearField() })
        }
    }
}