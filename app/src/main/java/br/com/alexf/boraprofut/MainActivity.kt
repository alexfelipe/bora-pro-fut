package br.com.alexf.boraprofut

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import br.com.alexf.boraprofut.features.game.navigation.gameRoute
import br.com.alexf.boraprofut.features.players.navigation.playersRoute
import br.com.alexf.boraprofut.features.players.navigation.playersScreen
import br.com.alexf.boraprofut.ui.theme.BoraProFutTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BoraProFutTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = gameRoute) {
                    playersScreen(navController)
                }
            }
        }
    }
}