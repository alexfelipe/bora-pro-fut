package br.com.alexf.boraprofut.features.drawteams

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.alexf.boraprofut.features.players.model.Player
import br.com.alexf.boraprofut.ui.components.BoraProFutButton
import br.com.alexf.boraprofut.ui.components.SelectPlayerPerTeam
import br.com.alexf.boraprofut.ui.theme.BoraProFutTheme

@Composable
fun DrawTeamsScreen(
    uiState: DrawTeamsUiState,
    modifier: Modifier = Modifier,
    onDecreasePlayers: () -> Unit,
    onIncreasePlayers: () -> Unit,
    onDrawRandomTeamsClick: () -> Unit,
) {
    val totalPlayers = uiState.players.size
    Column(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Sorteio de times", Modifier.padding(16.dp),
            style = MaterialTheme.typography.titleLarge,
        )
        SelectPlayerPerTeam(
            players = uiState.playersPerTeam,
            onDecreasePlayers = onDecreasePlayers,
            onIncreasePlayers = onIncreasePlayers,
            Modifier.align(Alignment.CenterHorizontally)
        )
        Column(
            Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BoraProFutButton(onClick = onDrawRandomTeamsClick) {
                Text(text = "Sorteiro aleatório")
            }
            Row(
                Modifier
                    .clip(RoundedCornerShape(15))
                    .fillMaxWidth()
                    .clickable {
                        uiState.onShowPlayersToggle()
                    }
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val (buttonIcon, contentDescription, buttonText) = remember(uiState.isShowPlayers) {
                    if (uiState.isShowPlayers) {
                        Triple(
                            Icons.Filled.KeyboardArrowDown,
                            "ícone do botão para mostrar jogadores",
                            "Esconder jogadores"
                        )
                    } else {
                        Triple(
                            Icons.Filled.KeyboardArrowRight,
                            "ícone do botão para esconder jogadores",
                            "Mostrar jogadores"
                        )
                    }
                }
                Icon(
                    imageVector = buttonIcon,
                    contentDescription = contentDescription
                )

                Text(text = "$buttonText ($totalPlayers)")
            }
        }

        if (uiState.isShowPlayers) {
            Column {
                Text(
                    text = "Jogadores",
                    Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    MaterialTheme.colorScheme.secondaryContainer,
                                    MaterialTheme.colorScheme.primaryContainer,
                                    MaterialTheme.colorScheme.background
                                )
                            )
                        )
                        .padding(16.dp),
                    style = MaterialTheme.typography.titleLarge
                )
                Column(
                    Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    uiState.players.forEach {
                        Text(
                            it.name, Modifier.fillMaxWidth(),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DrawTeamsScreenPreview() {
    BoraProFutTheme {
        DrawTeamsScreen(
            uiState = DrawTeamsUiState(),
            onDecreasePlayers = { /*TODO*/ },
            onDrawRandomTeamsClick = {},
            onIncreasePlayers = {}
        )
    }
}

@Preview
@Composable
fun DrawTeamsScreenDisplayingPlayersPreview() {
    BoraProFutTheme {
        DrawTeamsScreen(
            uiState = DrawTeamsUiState(
                players = setOf(
                    Player("Alex"),
                    Player("Thailan"),
                    Player("Daniel"),
                ),
                isShowPlayers = true
            ),
            onDecreasePlayers = { },
            onIncreasePlayers = { },
            onDrawRandomTeamsClick = {}
        )
    }
}