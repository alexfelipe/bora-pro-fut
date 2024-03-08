package br.com.alexf.boraprofut.features.timer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.alexf.boraprofut.ui.theme.BoraProFutTheme
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TimerScreen(
    uiState: TimerUiState,
    onMinuteTimeClick: (Long) -> Unit,
    onPauseClick: () -> Unit,
    onResumeClick: () -> Unit,
) {
    val currentTime = uiState.currentTime
    val isPause = uiState.isPause
    Column(modifier = Modifier.fillMaxSize()) {
        val seconds = TimeUnit.MILLISECONDS.toSeconds(currentTime).mod(60)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(currentTime)
        val textSeconds = if (seconds >= 10) "$seconds" else "0$seconds"
        val textMinutes = if (minutes >= 10) "$minutes" else "0$minutes"
        Text(
            text = "$textMinutes:$textSeconds",
            Modifier.align(Alignment.CenterHorizontally),
            style = LocalTextStyle.current.copy(
                fontSize = 128.sp
            )
        )
        FlowRow(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            uiState.times.forEach {
                Button(onClick = {
                    onMinuteTimeClick(it)
                }) {
                    Text(text = "$it min")
                }
            }
        }

        AnimatedVisibility(currentTime > 0L) {
            when (isPause) {
                true -> {
                    Button(
                        onClick = onResumeClick,
                        Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Text("Continuar")
                    }
                }
                false -> {
                    Button(
                        onClick = onPauseClick,
                        Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Text("Parar")
                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun TimerScreenPreview() {
    BoraProFutTheme {
        TimerScreen(
            uiState = TimerUiState(currentTime = 0L),
            onMinuteTimeClick = {},
            onPauseClick = {},
            onResumeClick = {}
        )
    }
}