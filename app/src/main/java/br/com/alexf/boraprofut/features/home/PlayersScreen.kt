package br.com.alexf.boraprofut.features.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.alexf.boraprofut.ui.theme.BoraProFutTheme

data class PlayersUiState(
    val players: String = "",
    val onPlayersChange: (String) -> Unit = {}
)

@Composable
fun PlayersScreen(
    uiState: PlayersUiState,
    modifier: Modifier = Modifier
) {
    Column(modifier.fillMaxSize()) {
        val players = uiState.players
        OutlinedTextField(
            value = players,
            onValueChange = uiState.onPlayersChange
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    BoraProFutTheme {
        PlayersScreen(
            uiState = PlayersUiState(
                players = "Alex\nFelipe",
                onPlayersChange = {}
            )
        )
    }
}
