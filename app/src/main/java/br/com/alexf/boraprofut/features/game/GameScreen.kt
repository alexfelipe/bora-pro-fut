package br.com.alexf.boraprofut.features.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.alexf.boraprofut.features.game.model.ReadyMadeGames
import br.com.alexf.boraprofut.features.game.model.TeamAtStandby
import coil.compose.AsyncImage

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    uiState: ReadyMadeGamesUiState
) {
    Column {
        Text(text = "Testando")

        LazyColumn(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(15.dp),
        ) {
            items(uiState.readyMadeGames) { time ->
                ReadyMadeGamesComponent(time = time)
            }
        }
    }
}

@Composable
private fun TeamAtStandbyComponent(
    modifier: Modifier = Modifier,
    time: TeamAtStandby
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
    ) {
        Team(nameTime = time.name)
    }
}

@Composable
private fun ReadyMadeGamesComponent(
    modifier: Modifier = Modifier,
    time: ReadyMadeGames
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
    ) {
        Column(modifier = modifier.padding(10.dp)) {
            Text(
                text = time.category,
                modifier = modifier.fillMaxWidth(),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight(700)
            )
            Row(
                modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Team(nameTime = time.timeA, image = time.image)
                Row(
                    modifier = modifier.fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "0")
                    Text(text = "X")
                    Text(text = "0")
                }
                Team(nameTime = time.timeB, image = time.image)
            }
        }
    }
}

@Composable
private fun Team(
    modifier: Modifier = Modifier,
    nameTime: String = "",
    image: String = ""
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = nameTime, fontSize = 14.sp)
        AsyncImage(
            model = image,
            contentDescription = null,
            modifier = modifier
                .padding(top = 16.dp)
                .size(30.dp)
        )
    }
}

@Preview
@Composable
fun ReadyMadeGamesComponentPreview() {
    ReadyMadeGamesComponent(time = ReadyMadeGames("teste1", "teste2", "teste3"))
}

@Preview(showSystemUi = true)
@Composable
private fun GameScreenPreview() {
    GameScreen(uiState = ReadyMadeGamesUiState(listOf()))
}