package br.com.alexf.boraprofut.features.players.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.alexf.boraprofut.features.game.navigation.gameRoute
import br.com.alexf.boraprofut.features.game.navigation.gameScreen
import br.com.alexf.boraprofut.features.players.PlayersScreen
import br.com.alexf.boraprofut.features.players.PlayersViewModel
import org.koin.androidx.compose.koinViewModel

const val playersRoute = "players"

fun NavGraphBuilder.playersScreen(navController: NavHostController) {

    composable(playersRoute) {
        val viewModel = koinViewModel<PlayersViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        Column {
            Box(modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(25))
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .clickable {
                 navController.navigate(gameRoute)
                }) {
                Text(
                    text = "Ir para tela de jogos".toUpperCase(Locale.current),
                    Modifier
                        .align(Alignment.Center)
                        .padding(16.dp),
                    style = LocalTextStyle.current.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            PlayersScreen(uiState, onDrawTeamsClick = {
                viewModel.drawTeams()
            })
        }
    }
    gameScreen()
}