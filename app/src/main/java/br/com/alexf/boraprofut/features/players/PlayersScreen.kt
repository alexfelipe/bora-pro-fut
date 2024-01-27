package br.com.alexf.boraprofut.features.players

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.alexf.boraprofut.R
import br.com.alexf.boraprofut.models.Player
import br.com.alexf.boraprofut.ui.components.BoraProFutButton
import br.com.alexf.boraprofut.ui.theme.BoraProFutTheme

@Composable
fun PlayersScreen(
    modifier: Modifier = Modifier,
    uiState: PlayersUiState, onSavePlayers: () -> Unit
) {
    val players = uiState.players
    Column(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(id = R.string.register_of_players),
                modifier.padding(16.dp),
                style = MaterialTheme.typography.titleLarge
            )
            Column(
                modifier.fillMaxWidth()
            ) {
                if (uiState.isSaving) {
                    Box(modifier = modifier.fillMaxWidth()) {
                        CircularProgressIndicator(
                            modifier.align(
                                Alignment.Center
                            )
                        )
                    }
                } else {
                    BoraProFutButton(
                        onClick = onSavePlayers, modifier.padding(16.dp)
                    ) {
                        Box(modifier = modifier.fillMaxWidth()) {
                            Text(
                                text = stringResource(R.string.save_players).toUpperCase(Locale.current),
                                modifier.align(Alignment.Center),
                                style = LocalTextStyle.current.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }
            AmountPlayers(uiState = uiState)
            OutlinedTextField(
                modifier = modifier
                    .heightIn(200.dp)
                    .fillMaxWidth()
                    .padding(16.dp),
                value = players,
                onValueChange = uiState.onPlayersChange,
                label = { Text(text = stringResource(R.string.players)) },
                shape = RoundedCornerShape(4)
            )
        }
    }
}

@Composable
fun AmountPlayers(modifier: Modifier = Modifier, uiState: PlayersUiState) {
    var amountPlayers by remember { mutableStateOf("") }
    Row(modifier = modifier.padding(top = 10.dp, start = 16.dp, end = 16.dp)) {
        Text(
            text = stringResource(R.string.players_registered),
            color = Color.Red,
            fontWeight = FontWeight(700)
        )
        Text(modifier = modifier.padding(start = 8.dp), text = amountPlayers)
    }
    amountPlayers = if (uiState.players.isNotBlank()) "${
        uiState.players.parseToPlayers(true).distinct().size
    }" else ""
    Column {
        Text(
            text = stringResource(id = R.string.names_duplicated),
            color = Color.Red,
            fontWeight = FontWeight(700),
            modifier = modifier.padding(top = 10.dp, start = 16.dp, end = 16.dp)
        )
        NamesDuplicates(player = uiState.players)
    }
}

fun findAllDuplicates(array: List<Player>): Set<Player> {
    val seen: MutableSet<Player> = mutableSetOf()
    return array.filter { !seen.add(it) }.toSet()
}

@Composable
fun NamesDuplicates(modifier: Modifier = Modifier, player: String) {
    LazyRow(
        modifier = modifier.padding(top = 6.dp, start = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(findAllDuplicates(player.parseToPlayers(false)).toList()) { index, item ->
            if (index != 0) Text(text = ", ", fontSize = 12.sp)
            Text(text = item.name, fontSize = 12.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NamesDuplicatesPreview() {
    NamesDuplicates(player = "alex \n felipe \n felipe")
}

@Preview(showBackground = true)
@Composable
fun AmountPlayersPreview() {
    BoraProFutTheme {
        Column {
            AmountPlayers(uiState = PlayersUiState(players = "Alex\nFelipe"))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    BoraProFutTheme {
        PlayersScreen(uiState = PlayersUiState(
            players = "Alex\nFelipe",
        ), onSavePlayers = {})
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenWithIsSavingStatePreview() {
    BoraProFutTheme {
        PlayersScreen(uiState = PlayersUiState(
            players = "Alex\nFelipe", isSaving = true
        ), onSavePlayers = {})
    }
}

