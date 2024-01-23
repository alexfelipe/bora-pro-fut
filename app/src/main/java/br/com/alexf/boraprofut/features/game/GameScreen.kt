package br.com.alexf.boraprofut.features.game

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.alexf.boraprofut.features.game.model.ReadyMadeGames
import br.com.alexf.boraprofut.features.game.model.TeamAtStandby

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    uiState: ReadyMadeGamesUiState
) {
    Column {
        Text(text = "Aguardando próxima partida", modifier = modifier.padding(16.dp))

        LazyColumn(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(15.dp),
        ) {
            items(uiState.teamAtStandby) { time ->
                TeamAtStandbyComponent(time = time)
            }
        }

        Text(text = "Jogos formados", modifier = modifier.padding(16.dp))

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
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, Color.Gray),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = modifier.fillMaxWidth().height(90.dp).background(Color.White)
    ) {
        Row(
            modifier = modifier.fillMaxWidth().fillMaxHeight().padding(16.dp),
            horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextGame(string = time.name,)
        }
    }
}

@Composable
private fun ReadyMadeGamesComponent(
    modifier: Modifier = Modifier,
    time: ReadyMadeGames
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, Color.Gray),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = modifier.fillMaxWidth().height(90.dp).background(Color.White)
    ) {
        Row(
            modifier = modifier.fillMaxWidth().fillMaxHeight().padding(16.dp),
            horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextGame(string = time.timeA, modifier = modifier.weight(1F))
            TextGame(string = "VS", modifier = modifier.weight(1F))
            TextGame(string = time.timeB, modifier = modifier.weight(1F))
        }
    }
}

@Composable
fun TextGame(
    modifier: Modifier = Modifier,
    string: String) {
    Text(
        text = string,
        textAlign = TextAlign.Center,
        modifier = modifier,
        fontSize = 14.sp
    )
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