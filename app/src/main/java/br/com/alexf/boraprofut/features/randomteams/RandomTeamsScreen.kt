package br.com.alexf.boraprofut.features.randomteams

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.alexf.boraprofut.features.players.model.Player
import br.com.alexf.boraprofut.ui.theme.BoraProFutTheme
import kotlin.random.Random

@Composable
fun RandomTeamsScreen(
    uiState: RandomTeamsUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Times sorteados",
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.size(16.dp))
        uiState.teams.forEachIndexed { index, players ->
            Column(Modifier.padding(vertical = 8.dp)) {
                Text(
                    text = "Time ${index + 1}",
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
                        )
                        .padding(16.dp),
                    style = MaterialTheme.typography.titleMedium
                )
                Column {
                    players.forEach { p ->
                        Text(
                            text = p.name,
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