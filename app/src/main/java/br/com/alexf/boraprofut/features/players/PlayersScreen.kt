package br.com.alexf.boraprofut.features.players

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
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
import br.com.alexf.boraprofut.ui.theme.BoraProFutTheme

@Composable
fun PlayersScreen(
    modifier: Modifier = Modifier,
    uiState: PlayersUiState,
    onSavePlayers: () -> Unit,
    onClear: () -> Unit,
) {
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
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    AnimatedVisibility(
                        visible = uiState.players.isNotBlank(), enter = fadeIn(initialAlpha = 0.0f)
                    ) {
                        Row(
                            Modifier
                                .clip(CircleShape)
                                .background(Color(0xFF8B0000))
                                .padding(horizontal = 8.dp)
                                .clickable { onClear() },
                            horizontalArrangement = Arrangement.Absolute.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                stringResource(id = R.string.clear), Modifier.padding(
                                    top = 8.dp, start = 8.dp, bottom = 8.dp, end = 2.dp
                                ), color = Color.White, fontWeight = FontWeight(700)
                            )
                            Icon(
                                imageVector = Icons.Outlined.Clear,
                                contentDescription = null,
                                Modifier
                                    .clip(CircleShape)
                                    .clickable {}
                                    .padding(top = 8.dp, start = 2.dp, bottom = 8.dp, end = 4.dp),
                                tint = Color.White,
                            )
                        }
                    }
                    AnimatedVisibility(
                        visible = uiState.players.isNotBlank() && uiState.amountPlayers.isNotBlank() && uiState.amountPlayers.toInt() > 3,
                        Modifier.weight(1f),
                        enter = fadeIn(initialAlpha = 0.0f)
                    ) {
                        Row(
                            Modifier
                                .clip(CircleShape)
                                .background(Color(0xFF006400))
                                .clickable { onSavePlayers() },
                            horizontalArrangement = Arrangement.Absolute.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                stringResource(id = R.string.save), Modifier.padding(
                                    top = 8.dp, start = 8.dp, bottom = 8.dp, end = 2.dp
                                ), color = Color.White, fontWeight = FontWeight(700)
                            )
                            Icon(
                                imageVector = Icons.Outlined.Done,
                                contentDescription = null,
                                Modifier
                                    .clip(CircleShape)
                                    .clickable {}
                                    .padding(top = 8.dp, start = 2.dp, bottom = 8.dp, end = 4.dp),
                                tint = Color.White,
                            )
                        }
                    }
                }
                OutlinedTextField(
                    modifier = Modifier
                        .heightIn(200.dp)
                        .fillMaxWidth()
                        .padding(16.dp),
                    value = uiState.players,
                    onValueChange = uiState.onPlayersChange,
                    label = { Text(text = stringResource(R.string.players)) },
                    shape = RoundedCornerShape(4)
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AmountPlayers(modifier: Modifier = Modifier, uiState: PlayersUiState) {
    AnimatedVisibility(
        visible = uiState.players.isNotBlank(), enter = fadeIn(initialAlpha = 0.0f)
    ) {
        Row(
            modifier.padding(
                top = 10.dp, start = 16.dp, end = 16.dp
            )
        ) {
            Text(
                text = stringResource(R.string.players_registered), fontWeight = FontWeight(700)
            )
            Text(
                modifier = Modifier.padding(start = 8.dp), text = uiState.amountPlayers
            )
        }
    }
    AnimatedVisibility(
        visible = uiState.duplicateNames.isNotEmpty(), enter = fadeIn(initialAlpha = 0.0f)
    ) {
        Column {
            Text(
                text = stringResource(id = R.string.names_duplicated),
                fontWeight = FontWeight(700),
                modifier = Modifier.padding(
                    top = 10.dp, start = 16.dp, end = 16.dp
                )
            )
            FlowRow(
                modifier = modifier
                    .padding(top = 6.dp, start = 16.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF8B0000))
                    .padding(4.dp)
            ) {
                uiState.duplicateNames.forEach { item ->
                    Text(
                        text = item.name, fontSize = 12.sp, color = Color.White
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun AmountPlayersPreview() {
    BoraProFutTheme {
        Column {
            AmountPlayers(uiState = PlayersUiState(players = "Alex\nFelipe"))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    BoraProFutTheme {
        PlayersScreen(uiState = PlayersUiState(
            players = "Alex\nFelipe",
        ), onSavePlayers = {}, onClear = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenWithIsSavingStatePreview() {
    BoraProFutTheme {
        PlayersScreen(uiState = PlayersUiState(
            players = "Alex\nFelipe", isSaving = true
        ), onSavePlayers = {}, onClear = {})
    }
}