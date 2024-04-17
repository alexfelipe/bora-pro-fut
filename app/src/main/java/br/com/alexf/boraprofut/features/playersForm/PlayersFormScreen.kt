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
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.alexf.boraprofut.R
import br.com.alexf.boraprofut.preview.UiModePreviews
import br.com.alexf.boraprofut.ui.theme.BoraProFutTheme
import br.com.alexf.boraprofut.ui.theme.ClearPlayersTextFieldContainerColor
import br.com.alexf.boraprofut.ui.theme.DuplicatesNamesContainerColor
import br.com.alexf.boraprofut.ui.theme.SavePlayersButtonContainerColor

const val GOAL_KEEPER_DIALOG = "GoalKeeperDialog"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayersFormScreen(
    uiState: PlayersUiState,
    onSavePlayers: () -> Unit,
    onClearPlayers: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isGoalKeeperToolTipVisible = uiState.isGoalKeeperToolTipVisible.collectAsState(initial = true)
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = stringResource(id = R.string.register_of_players))
            },
                actions = {
                    IconButton(onClick = uiState.onShowGoalKeeperToolTip) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = stringResource(R.string.show_add_goal_keeper_tip),
                        )
                    }
                })
        }
    ) { paddingValues ->
        Column(
            modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                Modifier
                    .weight(1f)
            ) {
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
                if (isGoalKeeperToolTipVisible.value) {
                    GoalKeeperToolTipAlert(
                        onDismissRequest = uiState.onHideGoalKeeperToolTip,
                        onConfirmation = uiState.onHideGoalKeeperToolTip,
                        dialogTitle = stringResource(R.string.add_goal_keeper),
                        dialogText = stringResource(id = R.string.mark_player_as_goal_keeper_message),
                        dialogButtonText = stringResource(R.string.got_it)
                    )
                }
            }
        }
    }

}

@Composable
fun GoalKeeperToolTipAlert(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    dialogButtonText: String
) {
    AlertDialog(
        title = { Text(text = dialogTitle) },
        text = { Text(text = dialogText) },
        modifier = Modifier.testTag(GOAL_KEEPER_DIALOG),
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(onClick = { onConfirmation() }) {
                Text(text = dialogButtonText)
            }
        })
}

@Preview
@Composable
fun GoalKeeperToolTipAlertPreview() {
    GoalKeeperToolTipAlert(
        onDismissRequest = {},
        onConfirmation = {},
        dialogTitle = "Adicionar Goleiro",
        dialogText = "Para adicionar um goleiro...",
        dialogButtonText = "Entendi"
    )
}

@UiModePreviews
@Composable
private fun PlayersFormScreenPreview() {
    BoraProFutTheme {
        PlayersFormScreen(uiState = PlayersUiState(
            players = "Alex\nFelipe",
        ), onSavePlayers = {}, onClearPlayers = {})
    }
}

@UiModePreviews
@Composable
private fun PlayersFormScreenWithIsSavingStatePreview() {
    BoraProFutTheme {
        PlayersFormScreen(uiState = PlayersUiState(
            players = "Alex\nFelipe", isSaving = true
        ), onSavePlayers = {}, onClearPlayers = {})
    }
}