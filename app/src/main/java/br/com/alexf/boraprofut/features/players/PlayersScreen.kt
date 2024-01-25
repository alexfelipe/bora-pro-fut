package br.com.alexf.boraprofut.features.players

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.alexf.boraprofut.R
import br.com.alexf.boraprofut.ui.components.BoraProFutButton
import br.com.alexf.boraprofut.ui.theme.BoraProFutTheme

@Composable
fun PlayersScreen(
    uiState: PlayersUiState,
    modifier: Modifier = Modifier,
    onSavePlayers: () -> Unit
) {
    val players = uiState.players
    Column(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Cadastrado de jogadores",
                Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleLarge
            )
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

            }
            OutlinedTextField(
                value = players,
                onValueChange = uiState.onPlayersChange,
                Modifier
                    .heightIn(200.dp)
                    .fillMaxWidth()
                    .padding(16.dp),
                label = {
                    Text(text = stringResource(R.string.players))
                },
                shape = RoundedCornerShape(4)
            )
        }
        Column(
            Modifier
                .fillMaxWidth()
        ) {
            if (uiState.isSaving) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
            } else {
                BoraProFutButton(
                    onClick = onSavePlayers,
                    Modifier.padding(16.dp)
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = stringResource(R.string.save_players)
                                .toUpperCase(Locale.current),
                            Modifier
                                .align(Alignment.Center),
                            style = LocalTextStyle.current.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    BoraProFutTheme {
        PlayersScreen(
            uiState = PlayersUiState(
                players = "Alex\nFelipe",
            ),
            onSavePlayers = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenWithIsSavingStatePreview() {
    BoraProFutTheme {
        PlayersScreen(
            uiState = PlayersUiState(
                players = "Alex\nFelipe",
                isSaving = true
            ),
            onSavePlayers = {}
        )
    }
}

