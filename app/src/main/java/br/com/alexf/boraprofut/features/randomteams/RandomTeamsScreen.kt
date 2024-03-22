package br.com.alexf.boraprofut.features.randomteams

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Sync
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.alexf.boraprofut.R
import br.com.alexf.boraprofut.models.Player
import br.com.alexf.boraprofut.models.Team
import br.com.alexf.boraprofut.preview.UiModePreviews
import br.com.alexf.boraprofut.ui.theme.BoraProFutTheme
import kotlin.random.Random

@Composable
fun RandomTeamsScreen(
    uiState: RandomTeamsUiState,
    modifier: Modifier = Modifier,
    onDrawTeamsAgain: () -> Unit = {}
) {
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.teams_drawn),
                Modifier
                    .weight(1f),
                style = MaterialTheme.typography.titleLarge
            )
            Icon(
                Icons.Outlined.Sync,
                contentDescription = stringResource(R.string.draw_teams_again_icon),
                Modifier
                    .clip(CircleShape)
                    .clickable { onDrawTeamsAgain() }
                    .padding(16.dp)
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        uiState.teams.forEachIndexed { index, team ->
            Column(Modifier.padding(vertical = 8.dp)) {
                Row(
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
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.team, index + 1),
                        Modifier
                            .padding(16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = stringResource(id = R.string.level, team.level),
                        Modifier
                            .padding(16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Column {
                    team.players.forEach { p ->
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = p.name,
                                Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                style = LocalTextStyle.current.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                text = "${p.level}",
                                Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                style = LocalTextStyle.current.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@UiModePreviews
@Composable
fun RandomTeamsScreenPreview() {
    BoraProFutTheme {
        RandomTeamsScreen(
            uiState = RandomTeamsUiState(
                teams = List(3) {
                    Team(players = List(6) {
                        Player(
                            "Jogador $it",
                            Random.nextInt(1, 10)
                        )
                    }.toSet())
                }
            )
        )
    }
}