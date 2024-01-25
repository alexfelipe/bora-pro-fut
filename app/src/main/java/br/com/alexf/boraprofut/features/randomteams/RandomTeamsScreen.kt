package br.com.alexf.boraprofut.features.randomteams

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.alexf.boraprofut.features.game.model.Team
import br.com.alexf.boraprofut.features.players.model.Player
import br.com.alexf.boraprofut.ui.components.BoraProFutButton
import br.com.alexf.boraprofut.ui.theme.BoraProFutTheme
import kotlin.random.Random

@Composable
fun RandomTeamsScreen(
    uiState: RandomTeamsUiState,
    modifier: Modifier = Modifier,
    goToGameScreen: (List<Team>) -> Unit = {},
) {
    val team = mutableListOf<Team>()
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        BoraProFutButton(
            onClick = { goToGameScreen(team.toList()) },
            Modifier.padding(16.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Sortear amistoso"
                        .toUpperCase(Locale.current),
                    Modifier
                        .align(Alignment.Center),
                    style = LocalTextStyle.current.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
        Text(
            text = "Times sorteados",
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.size(16.dp))
        uiState.teams.forEachIndexed { index, team ->
            val teamLevel = remember {
                team.sumOf {
                    it.level
                }
            }
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
                        text = "Time ${index + 1}",
                        Modifier
                            .padding(16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Nível $teamLevel",
                        Modifier
                            .padding(16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Column {
                    team.forEach { p ->
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

@Composable
fun generateRandomColor(): Color {
    val random = Random.Default
    return Color(
        red = random.nextFloat(),
        green = random.nextFloat(),
        blue = random.nextFloat(),
    )
}

@Preview(showBackground = true)
@Composable
fun RandomTeamsScreenPreview() {
    BoraProFutTheme {
        RandomTeamsScreen(
            uiState = RandomTeamsUiState(
                teams = listOf(
                    setOf(
                        Player("alex"),
                        Player("felipe")
                    ),
                    setOf(
                        Player("thailan"),
                        Player("godoy")
                    ),
                    setOf(
                        Player("daniel"),
                        Player("luche")
                    ),
                )
            )
        )
    }
}