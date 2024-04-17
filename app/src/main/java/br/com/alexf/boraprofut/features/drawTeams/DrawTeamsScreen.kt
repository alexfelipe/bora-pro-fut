package br.com.alexf.boraprofut.features.drawTeams

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Balance
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material.icons.outlined.SportsHandball
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.alexf.boraprofut.R
import br.com.alexf.boraprofut.models.Player
import br.com.alexf.boraprofut.preview.UiModePreviews
import br.com.alexf.boraprofut.ui.components.SelectPlayerPerTeam
import br.com.alexf.boraprofut.ui.theme.BoraProFutTheme
import br.com.alexf.boraprofut.ui.theme.DecreasePlayerLevelContainerColor
import br.com.alexf.boraprofut.ui.theme.DrawBalancedTeamsContainerPrimaryColor
import br.com.alexf.boraprofut.ui.theme.DrawBalancedTeamsContainerSecondaryColor
import br.com.alexf.boraprofut.ui.theme.DrawRandomTeamsContainerPrimaryColor
import br.com.alexf.boraprofut.ui.theme.DrawRandomTeamsContainerSecondaryColor
import br.com.alexf.boraprofut.ui.theme.EditPlayersButtonContainerPrimaryColor
import br.com.alexf.boraprofut.ui.theme.EditPlayersButtonContainerSecondaryColor
import br.com.alexf.boraprofut.ui.theme.IncreasePlayerLevelContainerColor
import br.com.alexf.boraprofut.ui.theme.PlayersContainerPrimaryColor
import br.com.alexf.boraprofut.ui.theme.PlayersContainerSecondaryColor
import kotlin.random.Random

const val PLAYERS_LIST = "PlayersList"
const val PLAYERS_LIST_ITEM = "PlayersListItem"
const val PLAYER_LEVEL_MENU = "PlayerLevelMenu"
const val EDIT_PLAYERS_BUTTON = "EditPlayersButton"

private class DrawOption(
    val title: String,
    val icon: ImageVector,
    val backgroundColor: Brush,
    val action: () -> Unit
)

@Composable
fun DrawTeamsScreen(
    uiState: DrawTeamsUiState,
    modifier: Modifier = Modifier,
    onDrawRandomTeamsClick: () -> Unit,
    onDrawBalancedTeamsClick: () -> Unit,
    onEditPlayersClick: () -> Unit,
) {
    val context = LocalContext.current
    val totalPlayers = uiState.players.size

    Column(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(R.string.teams_draw),
            Modifier.padding(16.dp),
            style = MaterialTheme.typography.titleLarge,
        )
        SelectPlayerPerTeam(
            players = uiState.playersPerTeam,
            onDecreasePlayers = uiState.onDecreasePlayersPerTeam,
            onIncreasePlayers = uiState.onIncreasePlayersPerTeam,
            Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        )
        val options = remember {
            listOf(
                DrawOption(
                    title = context.getString(R.string.random),
                    icon = Icons.Filled.People,
                    backgroundColor = Brush
                        .linearGradient(
                            colors = listOf(
                                DrawRandomTeamsContainerPrimaryColor,
                                DrawRandomTeamsContainerSecondaryColor
                            )
                        ),
                    action = onDrawRandomTeamsClick
                ),
                DrawOption(
                    title = context.getString(R.string.balanced),
                    icon = Icons.Filled.Balance,
                    backgroundColor = Brush
                        .linearGradient(
                            colors = listOf(
                                DrawBalancedTeamsContainerPrimaryColor,
                                DrawBalancedTeamsContainerSecondaryColor
                            )
                        ),
                    action = onDrawBalancedTeamsClick
                ),
            )
        }
        Row(
            Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            options.forEach { option ->
                Column(
                    Modifier
                        .clip(RoundedCornerShape(15))
                        .clickable { option.action() }
                        .background(option.backgroundColor)
                        .width(150.dp)
                        .heightIn(150.dp)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = option.title, style = TextStyle(
                            color = Color.White,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                        )
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Icon(
                        option.icon, contentDescription = option.title,
                        Modifier.size(64.dp),
                        tint = Color.White
                    )
                }
            }

        }
        Row(
            Modifier
                .padding(16.dp)
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
                        context.getString(R.string.icon_of_display_players_button),
                        context.getString(R.string.hide_players)
                    )
                } else {
                    Triple(
                        Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        context.getString(R.string.icon_of_hide_players_button),
                        context.getString(R.string.show_players)
                    )
                }
            }
            Icon(
                imageVector = buttonIcon,
                contentDescription = contentDescription
            )
            Text(
                text = "$buttonText ($totalPlayers)",
                style = LocalTextStyle.current.copy(
                    fontSize = 20.sp,
                )
            )
        }
        if (uiState.isShowPlayers) {
            if (uiState.players.isNotEmpty()) {
                Column {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.linearGradient(
                                    listOf(
                                        PlayersContainerPrimaryColor,
                                        PlayersContainerSecondaryColor,
                                        MaterialTheme.colorScheme.background
                                    ),
                                )
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(R.string.players, uiState.players.size),
                            Modifier.padding(16.dp),
                            style = MaterialTheme.typography.titleLarge.copy(Color.White)
                        )

                        Row(
                            Modifier
                                .padding(16.dp)
                                .clickable {
                                    onEditPlayersClick()
                                }
                                .clip(RoundedCornerShape(15))
                                .background(
                                    Brush.linearGradient(
                                        listOf(
                                            EditPlayersButtonContainerPrimaryColor,
                                            EditPlayersButtonContainerSecondaryColor,
                                        )
                                    )
                                )
                                .padding(8.dp)
                                .testTag(EDIT_PLAYERS_BUTTON),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = stringResource(R.string.players_edit_icon),
                                tint = Color.White
                            )
                            Text(
                                text = stringResource(R.string.edit),
                                style = LocalTextStyle.current.copy(color = Color.White)
                            )
                        }
                    }
                    PlayersList(uiState = uiState)
                }
            }
        }
    }
}

@Composable
fun PlayersList(uiState: DrawTeamsUiState) {
    Column(Modifier.testTag(PLAYERS_LIST)) {
        uiState.players.forEachIndexed { index, player ->
            PlayersListItem(uiState = uiState, player = player)
            if (index < uiState.players.size - 1) {
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun PlayersListItem(uiState: DrawTeamsUiState, player: Player) {
    Row(
        Modifier
            .fillMaxWidth()
            .testTag(PLAYERS_LIST_ITEM),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = {
            uiState.onGoalKeeperChange(player)
        }, modifier = Modifier.padding(vertical = 16.dp)) {
            if (player.isGoalKeeper == true) {
                Icon(
                    imageVector = Icons.Outlined.SportsHandball,
                    contentDescription = stringResource(R.string.goal_keeper_icon)
                )
            } else {
                Icon(
                    imageVector = Icons.Outlined.SportsHandball,
                    contentDescription = stringResource(R.string.not_goal_keeper_icon),
                    tint = if (isSystemInDarkTheme()) {
                        Color.White.copy(alpha = 0.2f)
                    } else {
                        Color.Black.copy(alpha = 0.2f)
                    },
                )
            }

        }
        Text(
            text = if (player.isGoalKeeper == true) {
                "${player.name.trim()} (G)"
            } else {
                player.name
            },
            Modifier.weight(1f),
            style = LocalTextStyle.current.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        )
        if (player.isGoalKeeper == false) {
            PlayerLevelMenu(uiState = uiState, player = player)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlayerLevelMenu(uiState: DrawTeamsUiState, player: Player) {
    Row(
        Modifier
            .padding(end = 16.dp)
            .testTag(PLAYER_LEVEL_MENU),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .combinedClickable(
                    onClick = {
                        uiState.onDecreasePlayerLevel(player)
                    },
                    onLongClick = {
                        uiState.onDecreasePlayerLevel(player)
                    }
                )
                .background(
                    DecreasePlayerLevelContainerColor
                        .copy(alpha = 0.8f)
                )
                .padding(4.dp)
        ) {
            Icon(
                Icons.Outlined.Remove,
                contentDescription = stringResource(id = R.string.decrease_level),
                tint = Color.White
            )
        }
        Text(
            text = "${player.level}",
            Modifier.width(28.dp),
            style = LocalTextStyle.current.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        )

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .combinedClickable(
                    onClick = {
                        uiState.onIncreasePlayerLevel(player)
                    },
                    onLongClick = {

                        uiState.onIncreasePlayerLevel(player)
                    }
                )
                .background(
                    IncreasePlayerLevelContainerColor
                        .copy(alpha = 0.8f)
                )
                .padding(4.dp)
        ) {
            Icon(
                Icons.Outlined.Add,
                contentDescription = stringResource(id = R.string.increase_level),
                tint = Color.White
            )
        }
    }
}

@UiModePreviews
@Composable
fun DrawTeamsScreenPreview() {
    BoraProFutTheme {
        DrawTeamsScreen(
            uiState = DrawTeamsUiState(isShowPlayers = false),
            onDrawRandomTeamsClick = {},
            onDrawBalancedTeamsClick = {},
            onEditPlayersClick = {}
        )
    }
}

@UiModePreviews
@Composable
fun PlayersListPreview() {

    val players = mutableSetOf(
        Player(name = "Alex", level = Random.nextInt(1, 10), isGoalKeeper = true),
        Player(name = "Thailan", level = Random.nextInt(1, 10)),
        Player(name = "Daniel", level = Random.nextInt(1, 10)),
        Player(name = "Joao", level = 10),
        Player(name = "Janssen", level = Random.nextInt(1, 10))
    )

    BoraProFutTheme {
        Surface {
            PlayersList(
                uiState = DrawTeamsUiState(
                    players = players
                )
            )
        }
    }
}

@UiModePreviews
@Composable
fun DrawTeamsScreenDisplayingPlayersPreview() {
    BoraProFutTheme {
        Surface {
            DrawTeamsScreen(
                uiState = DrawTeamsUiState(
                    players = setOf(
                        Player(
                            name = "Alex",
                            level = Random.nextInt(1, 10),
                            isGoalKeeper = true
                        ),
                        Player(name = "Thailan", level = Random.nextInt(1, 10)),
                        Player(name = "Daniel", level = Random.nextInt(1, 10)),
                        Player(name = "Joao", level = 10),
                    ),
                    isShowPlayers = true
                ),
                onDrawRandomTeamsClick = {},
                onDrawBalancedTeamsClick = {},
                onEditPlayersClick = {}
            )
        }
    }
}