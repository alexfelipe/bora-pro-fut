package br.com.alexf.futzinmaroto.features.game

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.alexf.futzinmaroto.R
import br.com.alexf.futzinmaroto.features.game.model.ReadyMadeGames

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    uiState: ReadyMadeGamesUiState
) {
    Column {
        if (uiState.teamAtStandby.isNotEmpty()) {
            Text(
                text = stringResource(id = R.string.waiting_next_game),
                modifier = modifier.padding(16.dp)
            )
            LazyColumn(
                modifier = modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(15.dp),
            ) {
                items(uiState.teamAtStandby) { time ->
                    ReadyMadeGamesComponent(timeA = time.name)
                }
            }
        }
        Text(text = stringResource(id = R.string.formed_games), modifier = modifier.padding(16.dp))
        LazyColumn(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(15.dp),
        ) {
            items(uiState.readyMadeGames) { time ->
                ReadyMadeGamesComponent(timeA = time.timeA, timeB = time.timeB)
            }
        }
    }
}


@Composable
private fun ReadyMadeGamesComponent(
    modifier: Modifier = Modifier,
    timeA: String,
    timeB: String = ""
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, Color.Gray),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = modifier
            .fillMaxWidth()
            .height(90.dp)
            .background(Color.White)
    ) {
        Row(
            modifier = modifier.fillMaxWidth().fillMaxHeight().padding(16.dp),
            horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextGame(string = timeA, modifier = modifier.weight(1F))
            if (timeB.isNotBlank()) {
                TextGame(string = stringResource(id = R.string.match_symbol), modifier = modifier.weight(1F))
                TextGame(string = timeB, modifier = modifier.weight(1F))
            }
        }
    }
}

@Composable
fun TextGame(
    modifier: Modifier = Modifier,
    string: String
) {
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
    ReadyMadeGamesComponent(timeA = "Flamengo", timeB = "Vasco")
}

@Preview(showSystemUi = true)
@Composable
private fun GameScreenPreview() {
    GameScreen(
        uiState = ReadyMadeGamesUiState(
            readyMadeGames = listOf(
                ReadyMadeGames(
                    "Flamengo",
                    timeB = "Vasco"
                )
            )
        )
    )
}