package br.com.alexf.boraprofut.features.players

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.alexf.boraprofut.R
import br.com.alexf.boraprofut.ui.theme.BoraProFutTheme

@Composable
fun PlayersScreen(
    uiState: PlayersUiState,
    modifier: Modifier = Modifier,
    onDrawTeamsClick: () -> Unit
) {
    val players = uiState.players
    val playersAmountPerTeam = uiState.playersPerTeam
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.players_amount_per_team),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.size(16.dp))
            Row(
                Modifier.height(48.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val iconModifier = Modifier
                    .fillMaxHeight()
                    .width(40.dp)
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.decreases_players_amount_per_team),
                    iconModifier
                        .clickable { uiState.onDecreasesPlayersPerTeam() }
                )
                Box(Modifier.fillMaxHeight()) {
                    Text(
                        text = "$playersAmountPerTeam",
                        Modifier
                            .align(Alignment.Center),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                Icon(
                    Icons.Filled.ArrowForward,
                    contentDescription = stringResource(R.string.increases_players_amount_per_team),
                    iconModifier
                        .clickable { uiState.onIncreasesPlayersPerTeam() }
                )

            }
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
            }
        )
        Box(
            modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(25))
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .clickable {
                    onDrawTeamsClick()
                }
        ) {
            Text(
                text = stringResource(R.string.draw_teams)
                    .toUpperCase(Locale.current),
                Modifier
                    .align(Alignment.Center)
                    .padding(16.dp),
                style = LocalTextStyle.current.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Column {
            uiState.drawedTeams.forEachIndexed { index, players ->
                Text(text = "Time ${index + 1}")
                for (player in players) {
                    Text(text = player.name)
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
            onDrawTeamsClick = {}
        )
    }
}
