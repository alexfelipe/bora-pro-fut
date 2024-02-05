package br.com.alexf.boraprofut.features.playersForm

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import br.com.alexf.boraprofut.ui.theme.ClearPlayersTextFieldContainerColor
import br.com.alexf.boraprofut.ui.theme.DuplicatesNamesContainerColor
import br.com.alexf.boraprofut.ui.theme.SavePlayersButtonContainerColor

@Composable
fun PlayersScreen(
    modifier: Modifier = Modifier,
    uiState: PlayersUiState,
    onSavePlayers: () -> Unit,
    onClearPlayers: () -> Unit,
) {
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            Modifier
                .weight(1f)
        ) {
            Text(
                text = stringResource(id = R.string.register_of_players),
                Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleLarge
            )
            AnimatedVisibility(
                visible = uiState.players.isNotBlank(),
                enter = fadeIn(initialAlpha = 0.0f)
            ) {
                Row(
                    modifier.padding(
                        top = 10.dp, start = 16.dp, end = 16.dp
                    )
                ) {
                    Text(
                        text = stringResource(R.string.players_registered),
                        fontWeight = FontWeight(700)
                    )
                    Text(
                        text = "${uiState.amountPlayers}",
                        Modifier.padding(start = 8.dp),
                    )
                }
            }
            AnimatedVisibility(
                visible = uiState.duplicateNames.isNotEmpty(),
                enter = fadeIn(initialAlpha = 0.0f)
            ) {
                Column(Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        text = stringResource(id = R.string.names_duplicated),
                        Modifier.padding(top = 10.dp),
                        fontWeight = FontWeight(700)
                    )

                    Text(
                        text = uiState.duplicateNames, Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(DuplicatesNamesContainerColor)
                            .padding(
                                horizontal = 12.dp,
                                vertical = 8.dp
                            ), color = Color.White, fontSize = 12.sp
                    )
                }
            }
            Column(
                Modifier.padding(top = 16.dp)
            ) {
                Row(
                    Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    AnimatedVisibility(
                        uiState.players.isNotBlank(),
                        modifier,
                        fadeIn(initialAlpha = 0.0f)
                    ) {

                        Row(
                            Modifier
                                .clip(CircleShape)
                                .background(ClearPlayersTextFieldContainerColor)
                                .padding(horizontal = 8.dp, vertical = 8.dp)
                                .clickable { onClearPlayers() },
                            horizontalArrangement = Arrangement.Absolute.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                stringResource(id = R.string.clear),
                                color = Color.White,
                                fontWeight = FontWeight(700)
                            )
                            Spacer(Modifier.size(4.dp))
                            Icon(
                                imageVector = Icons.Outlined.Clear,
                                contentDescription = null,
                                Modifier.clip(CircleShape),
                                tint = Color.White,
                            )
                        }
                    }

                    AnimatedVisibility(
                        uiState.isShowSaveButton(),
                        modifier.weight(1f),
                        fadeIn(initialAlpha = 0.0f)
                    ) {

                        Row(
                            Modifier
                                .clip(CircleShape)
                                .background(SavePlayersButtonContainerColor)
                                .padding(horizontal = 8.dp, vertical = 8.dp)
                                .clickable { onSavePlayers() },
                            horizontalArrangement = Arrangement.Absolute.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                stringResource(id = R.string.save),
                                color = Color.White,
                                fontWeight = FontWeight(700)
                            )
                            Spacer(Modifier.size(4.dp))
                            Icon(
                                imageVector = Icons.Outlined.Done,
                                contentDescription = null,
                                Modifier.clip(CircleShape),
                                tint = Color.White,
                            )
                        }
                    }
                }
            }
            OutlinedTextField(
                value = uiState.players,
                onValueChange = uiState.onPlayersChange,
                Modifier
                    .heightIn(200.dp)
                    .fillMaxWidth()
                    .padding(16.dp),
                label = { Text(text = stringResource(R.string.players_list)) },
                shape = RoundedCornerShape(4)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    BoraProFutTheme {
        PlayersScreen(uiState = PlayersUiState(
            players = "Alex\nFelipe",
        ), onSavePlayers = {}, onClearPlayers = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenWithIsSavingStatePreview() {
    BoraProFutTheme {
        PlayersScreen(uiState = PlayersUiState(
            players = "Alex\nFelipe", isSaving = true
        ), onSavePlayers = {}, onClearPlayers = {})
    }
}