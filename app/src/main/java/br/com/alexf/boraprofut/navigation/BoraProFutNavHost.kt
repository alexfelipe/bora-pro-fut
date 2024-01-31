package br.com.alexf.boraprofut.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import br.com.alexf.boraprofut.features.balancedteams.balancedTeams
import br.com.alexf.boraprofut.features.balancedteams.navigateToBalancedTeams
import br.com.alexf.boraprofut.features.drawteams.drawTeams
import br.com.alexf.boraprofut.features.drawteams.drawTeamsRoute
import br.com.alexf.boraprofut.features.drawteams.navigateToDrawTeams
import br.com.alexf.boraprofut.features.game.navigation.gameScreen
import br.com.alexf.boraprofut.features.game.navigation.navigateToGameScreen
import br.com.alexf.boraprofut.features.players.navigateToPlayersScreen
import br.com.alexf.boraprofut.features.players.playersRoute
import br.com.alexf.boraprofut.features.players.playersScreen
import br.com.alexf.boraprofut.features.randomteams.navigateToRandomTeams
import br.com.alexf.boraprofut.features.randomteams.randomTeams

@Composable
fun BoraProFutNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = drawTeamsRoute
    ) {
        playersScreen(onNavigateToDrawScreen = {
            navController.navigateToDrawTeams()
        })
        drawTeams(
            onNavigateToRandomTeams = {
                navController.navigateToRandomTeams()
            },
            onNavigateToBalancedTeams = {
                navController.navigateToBalancedTeams()
            },
            onNavigateToPlayersScreen = {
                navController.navigateToPlayersScreen()
            }
        )
        balancedTeams()
        randomTeams(
            onNavigateToGameScreen = {
                navController.navigateToGameScreen()
            }
        )
        gameScreen()
    }
}
