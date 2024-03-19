package br.com.alexf.boraprofut.features.timer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.alexf.boraprofut.R
import br.com.alexf.boraprofut.preview.UiModePreviews
import br.com.alexf.boraprofut.ui.theme.BoraProFutTheme
import br.com.alexf.boraprofut.ui.theme.ContinueButtonColor
import br.com.alexf.boraprofut.ui.theme.PauseButtonColor
import br.com.alexf.boraprofut.ui.theme.RestartButtonColor
import br.com.alexf.boraprofut.ui.theme.RestartButtonContentColor
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TimerScreen(
    uiState: TimerUiState,
    onMinuteTimeClick: (Long) -> Unit,
    onPauseClick: () -> Unit,
    onResumeClick: () -> Unit,
    onRestartClick: () -> Unit
) {
    val currentTime = uiState.currentTime
    val isPause = uiState.isPause
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center
    ) {
        val seconds = TimeUnit.MILLISECONDS.toSeconds(currentTime).mod(60)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(currentTime)
        val textSeconds = if (seconds >= 10) "$seconds" else "0$seconds"
        val textMinutes = if (minutes >= 10) "$minutes" else "0$minutes"
        Box(
            Modifier
                .size(300.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
                .align(Alignment.CenterHorizontally)
        ) {
            val animatedProgression by animateFloatAsState(
                targetValue = uiState.timerProgress,
                label = "timer circular progress animation",
                animationSpec = tween(durationMillis = 1000, easing = LinearEasing),
            )
            val animatedCircularProgressColor by animateColorAsState(
                when {
                    uiState.timerProgress < 0.5f -> Color.Green.copy(0.5f)
                    uiState.timerProgress < 0.9f -> Color.Yellow.copy(0.8f)
                    else -> Color.Red
                },
                label = "color"
            )
            CircularProgressIndicator(
                progress = {
                    animatedProgression
                },
                Modifier
                    .align(Alignment.Center)
                    .size(300.dp),
                color = animatedCircularProgressColor,
                strokeWidth = 10.dp,
            )
            Text(
                text = "$textMinutes:$textSeconds",
                Modifier.align(Alignment.Center),
                style = LocalTextStyle.current.copy(
                    fontSize = 100.sp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        AnimatedVisibility(visible = uiState.isPause) {
            FlowRow(
                Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                uiState.times.forEach {
                    Button(
                        onClick = {
                            onMinuteTimeClick(it)
                        },
                        Modifier
                            .height(60.dp)
                    ) {
                        Text(
                            text = "$it min", style = LocalTextStyle.current.copy(
                                fontSize = 20.sp
                            )
                        )
                    }
                }
            }
        }
        AnimatedVisibility(currentTime > 0L) {
            Box(modifier = Modifier.fillMaxWidth()) {
                when (isPause) {
                    true -> {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Button(
                                onClick = onResumeClick,
                                Modifier
                                    .padding(vertical = 16.dp)
                                    .height(60.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = ContinueButtonColor
                                )
                            ) {
                                Icon(
                                    Icons.Filled.PlayArrow,
                                    contentDescription = stringResource(R.string.play_button_content_description)
                                )
                                Spacer(modifier = Modifier.size(8.dp))
                                Text(
                                    stringResource(R.string.resume),
                                    style = LocalTextStyle.current.copy(
                                        fontSize = 20.sp
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Button(
                                onClick = onRestartClick,
                                Modifier
                                    .padding(vertical = 16.dp)
                                    .height(60.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = RestartButtonColor
                                )
                            ) {
                                Icon(
                                    Icons.Filled.RestartAlt,
                                    contentDescription = stringResource(R.string.restart_button_content_description),
                                    tint = RestartButtonContentColor
                                )
                                Spacer(modifier = Modifier.size(8.dp))
                                Text(
                                    stringResource(R.string.restart),
                                    style = LocalTextStyle.current.copy(
                                        fontSize = 20.sp
                                    ),
                                    color = RestartButtonContentColor
                                )
                            }
                        }
                    }

                    false -> {

                        Button(
                            onClick = onPauseClick,
                            Modifier
                                .padding(16.dp)
                                .height(60.dp)
                                .align(Alignment.Center),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PauseButtonColor
                            )
                        ) {
                            Icon(Icons.Filled.Pause, contentDescription = stringResource(R.string.pause_button_content_description))
                            Spacer(modifier = Modifier.size(8.dp))
                            Text(
                                stringResource(R.string.pause),
                                style = LocalTextStyle.current.copy(
                                    fontSize = 20.sp,
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}


@UiModePreviews
@Composable
private fun TimerScreenPreview() {
    BoraProFutTheme {
        Surface {
            TimerScreen(
                uiState = TimerUiState(currentTime = 0L),
                onMinuteTimeClick = {},
                onPauseClick = {},
                onResumeClick = {},
                onRestartClick = {}
            )
        }
    }
}

@Preview(name = "timer with time")
@Composable
private fun TimerScreen1Preview() {
    BoraProFutTheme {
        Surface {
            TimerScreen(
                uiState = TimerUiState(
                    currentTime = 1000L
                ),
                onMinuteTimeClick = {},
                onPauseClick = {},
                onResumeClick = {},
                onRestartClick = {})
        }
    }
}

@Preview(name = "timer in resume state")
@Composable
private fun TimerScreen2Preview() {
    BoraProFutTheme {
        Surface {
            TimerScreen(
                uiState = TimerUiState(
                    currentTime = 1000L,
                    isPause = false
                ),
                onMinuteTimeClick = {},
                onPauseClick = {},
                onResumeClick = {},
                onRestartClick = {})
        }
    }
}

@Preview(name = "timer in pause state")
@Composable
private fun TimerScreen3Preview() {
    BoraProFutTheme {
        Surface {
            TimerScreen(
                uiState = TimerUiState(
                    currentTime = 1000L,
                    isPause = true
                ),
                onMinuteTimeClick = {},
                onPauseClick = {},
                onResumeClick = {},
                onRestartClick = {})
        }
    }
}

@Preview(name = "timer with progress below than 50%")
@Composable
private fun TimerScreen4Preview() {
    BoraProFutTheme {
        Surface {
            TimerScreen(
                uiState = TimerUiState(
                    currentTime = 1000L,
                    isPause = true,
                    timerProgress = 0.49f
                ),
                onMinuteTimeClick = {},
                onPauseClick = {},
                onResumeClick = {},
                onRestartClick = {})
        }
    }
}

@Preview(name = "timer with progress below than 89%")
@Composable
private fun TimerScreen5Preview() {
    BoraProFutTheme {
        Surface {
            TimerScreen(
                uiState = TimerUiState(
                    currentTime = 1000L,
                    isPause = true,
                    timerProgress = 0.74f
                ),
                onMinuteTimeClick = {},
                onPauseClick = {},
                onResumeClick = {},
                onRestartClick = {})
        }
    }
}


@Preview(name = "timer with progress in 90%")
@Composable
private fun TimerScreen6Preview() {
    BoraProFutTheme {
        Surface {
            TimerScreen(
                uiState = TimerUiState(
                    currentTime = 1000L,
                    isPause = true,
                    timerProgress = 0.9f
                ),
                onMinuteTimeClick = {},
                onPauseClick = {},
                onResumeClick = {},
                onRestartClick = {})
        }
    }
}