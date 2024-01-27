package br.com.alexf.boraprofut.features.players

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.alexf.boraprofut.R
import br.com.alexf.boraprofut.features.players.model.Player
import br.com.alexf.boraprofut.ui.components.BoraProFutButton
import br.com.alexf.boraprofut.ui.theme.BoraProFutTheme

@Composable
fun PlayersScreen(
    uiState: PlayersUiState,
    modifier: Modifier = Modifier,
    onSavePlayers: () -> Unit
) {
    val players = uiState.players
    val amountPlayers = remember { mutableStateOf("") }
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
            OutlinedTextField(
                value = players,
                onValueChange = uiState.onPlayersChange,
                Modifier
                    .heightIn(200.dp)
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text(text = stringResource(R.string.players)) }
            )
            Row(modifier = Modifier.padding(top = 10.dp, start = 16.dp, end = 16.dp)) {
                Text(text = "Jogadores cadastrados: ", fontWeight = FontWeight(700))
                Text(text = amountPlayers.value)
            }
            amountPlayers.value = if (uiState.players.isNotBlank()) "${
                uiState.players.parseToPlayers(true).distinct().size
            }" else ""
            Column {
                Text(
                    text = "Nomes duplicados serão mesclados: ",
                    fontWeight = FontWeight(700),
                    modifier = Modifier.padding(top = 10.dp, start = 16.dp, end = 16.dp)
                )
                LazyRow(
                    modifier = Modifier
                        .padding(top = 6.dp, start = 16.dp)
                        .fillMaxWidth()
                ) {
                    itemsIndexed(findAllDuplicates(uiState.players.parseToPlayers(false)).toList()) { index, item ->
                        if (index != 0) Text(text = ", ", fontSize = 12.sp)
                        Text(text = item.name, fontSize = 12.sp)
                    }
                }
            }

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

fun findAllDuplicates(array: List<Player>): Set<Player> {
    val seen: MutableSet<Player> = mutableSetOf()
    return array.filter { !seen.add(it) }.toSet()
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    BoraProFutTheme {
        PlayersScreen(
            uiState = PlayersUiState(
                players = "",
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


