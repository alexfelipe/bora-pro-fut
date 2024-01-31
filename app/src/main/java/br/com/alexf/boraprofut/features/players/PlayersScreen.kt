package br.com.alexf.boraprofut.features.players

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.alexf.boraprofut.R
import br.com.alexf.boraprofut.models.Player
import br.com.alexf.boraprofut.ui.theme.BoraProFutTheme
import okhttp3.internal.wait

@Composable
fun PlayersScreen(
    modifier: Modifier = Modifier,
    uiState: PlayersUiState,
    onSavePlayers: () -> Unit,
    onClearTheField: () -> Unit,
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
                text = stringResource(id = R.string.register_of_players),
                Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleLarge
            )
            AmountPlayers(uiState = uiState)
            Column(Modifier.padding(top = 16.dp)) {
                Row(modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(Modifier.clip(CircleShape).background(Color.Green).weight(1f),  horizontalArrangement = Arrangement.Absolute.SpaceBetween,  verticalAlignment = Alignment.CenterVertically) {
                        Text(stringResource(id = R.string.save), Modifier.padding(8.dp), color = Color.Black, fontWeight = FontWeight(700))
                        Icon(
                            imageVector = Icons.Outlined.Done,
                            contentDescription = null,
                            Modifier.clip(CircleShape).clickable {}.padding(8.dp)
                        )
                    }
                    Row(Modifier.clip(CircleShape).background(Color.Red), horizontalArrangement = Arrangement.Absolute.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text(stringResource(id = R.string.clear), Modifier.padding(8.dp), color = Color.Black, fontWeight = FontWeight(700))
                        Icon(
                            imageVector = Icons.Outlined.Clear,
                            contentDescription = null,
                            Modifier.clip(CircleShape).clickable {}.padding(8.dp)
                        )
                    }
                }
                OutlinedTextField(
                    modifier = Modifier
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
}

@Composable
fun AmountPlayers(modifier: Modifier = Modifier, uiState: PlayersUiState) {
    Row(
        modifier.padding(
            top = 10.dp,
            start = 16.dp,
            end = 16.dp
        )
    ) {
        Text(
            text = stringResource(R.string.players_registered),
            fontWeight = FontWeight(700)
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = uiState.amountPlayers
        )
    }
    Column {
        Text(
            text = stringResource(id = R.string.names_duplicated),
            fontWeight = FontWeight(700),
            modifier = Modifier.padding(
                top = 10.dp,
                start = 16.dp,
                end = 16.dp
            )
        )
        DuplicateNames(duplicateNames = uiState.duplicateNames)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DuplicateNames(modifier: Modifier = Modifier, duplicateNames: List<Player>) {
    FlowRow(modifier = modifier.padding(top = 6.dp, start = 16.dp)) {
        duplicateNames.forEach { item ->
            Text(
                text = item.name,
                fontSize = 12.sp,
                color = Color.Red
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NamesDuplicatesPreview() {
    DuplicateNames(duplicateNames = listOf(Player("alex"), Player("felipe"), Player("felipe")))
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
        ), onSavePlayers = {}, onClearTheField = {})
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenWithIsSavingStatePreview() {
    BoraProFutTheme {
        PlayersScreen(uiState = PlayersUiState(
            players = "Alex\nFelipe", isSaving = true
        ), onSavePlayers = {}, onClearTheField = {})
    }
}