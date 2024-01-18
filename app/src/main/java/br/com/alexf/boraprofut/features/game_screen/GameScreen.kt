package br.com.alexf.boraprofut.features.game_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import coil.compose.AsyncImage

data class NameTime(
    val timeA: String,
    val timeB: String,
    val category: String,
)

@Composable
fun GameScreen() {
    val list = listOf(
        NameTime("Flamengo", "Vasco", "Grupo D - Dia da partida 5 X 3"),
        NameTime("São Paulo", "Flamengo", "Grupo C - Dia da partida 1 X 5")
    )
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(15.dp),
    ) {
        items(list) { time ->
            GameGroup(time)
        }
    }
}

@Composable
private fun GameGroup(time: NameTime) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.fillMaxWidth().height(150.dp)
    ) {
        Column (modifier = Modifier.padding(10.dp)) {
            Text(text = time.category, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Team(time.timeA)
                Row(
                    modifier = Modifier.fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "5")
                    Text(text = "X")
                    Text(text = "1")
                }
                Team(time.timeB)
            }
        }
    }
}

@Composable
private fun Team(nameTime: String) {
    Column(
        modifier = Modifier.fillMaxHeight().padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = nameTime)
        AsyncImage(
            model = "https://conteudo.imguol.com.br/c/esporte/94/2022/07/28/simbolo-do-clube-laguna-o-primeiro-time-vegano-do-brasil-1659039926161_v2_450x450.jpg",
            contentDescription = "Translated description of what the image contains",
            modifier = Modifier.padding(top = 15.dp).size(30.dp)
        )
    }
}

@Preview
@Composable
fun GameGroupPreview() {
    GameGroup(NameTime("teste1", "teste2", "teste3"))
}

@Preview(showSystemUi = true)
@Composable
private fun GameScreenPreview() {
    GameScreen()
}