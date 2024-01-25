package br.com.alexf.boraprofut

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import br.com.alexf.boraprofut.features.drawteams.drawTeams
import br.com.alexf.boraprofut.features.drawteams.navigateToDrawTeams
import br.com.alexf.boraprofut.features.game.navigation.gameScreen
import br.com.alexf.boraprofut.features.game.navigation.navigateToGameScreen
import br.com.alexf.boraprofut.features.players.playersRoute
import br.com.alexf.boraprofut.features.players.playersScreen
import br.com.alexf.boraprofut.features.randomteams.navigateToRandomTeams
import br.com.alexf.boraprofut.features.randomteams.randomTeams
import br.com.alexf.boraprofut.ui.theme.BoraProFutTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BoraProFutTheme {
                val navController = rememberNavController()
                Surface(color = MaterialTheme.colorScheme.background) {
                    NavHost(
                        navController = navController,
                        startDestination = playersRoute
                    ) {
                        playersScreen(onNavigateToDrawScreen = {
                            navController.navigateToDrawTeams()
                        })
                        drawTeams(
                            onNavigateToRandomTeams = {
                                navController.navigateToRandomTeams()
                            }
                        )
                        randomTeams(
                            onNavigateToGameScreen = {
                                navController.navigateToGameScreen()
                            }
                        )
                        gameScreen()
                    }
                }
            }
        }
    }
}


