package br.com.alexf.boraprofut.features.timer

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.koin.androidx.compose.koinViewModel

const val timerRoute = "timer"

fun NavGraphBuilder.timer() {
    composable(timerRoute) {
        val viewModel = koinViewModel<TimerViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        TimerScreen(uiState,
            onMinuteTimeClick = {
                viewModel.setMinutes(it)
            },
            onPauseClick = {
                viewModel.pause()
            },
            onResumeClick = {
                viewModel.resume()
            })
    }
}