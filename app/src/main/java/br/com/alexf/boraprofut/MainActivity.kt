package br.com.alexf.boraprofut

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import br.com.alexf.boraprofut.features.drawteams.drawTeams
import br.com.alexf.boraprofut.features.drawteams.navigateToDrawTeams
import br.com.alexf.boraprofut.features.players.playersRoute
import br.com.alexf.boraprofut.features.players.playersScreen
import br.com.alexf.boraprofut.ui.theme.BoraProFutTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BoraProFutTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = playersRoute
                ) {
                    playersScreen(onNavigateToDrawScreen = {
                        navController.navigateToDrawTeams()
                    })
                    drawTeams()
                }
            }
        }
    }
}