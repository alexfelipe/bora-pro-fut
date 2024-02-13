package br.com.alexf.futzinmaroto.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import br.com.alexf.futzinmaroto.features.balancedTeams.balancedTeams
import br.com.alexf.futzinmaroto.features.balancedTeams.navigateToBalancedTeams
import br.com.alexf.futzinmaroto.features.drawTeams.drawTeams
import br.com.alexf.futzinmaroto.features.drawTeams.drawTeamsRoute
import br.com.alexf.futzinmaroto.features.drawTeams.navigateToDrawTeams
import br.com.alexf.futzinmaroto.features.game.navigation.gameScreen
import br.com.alexf.futzinmaroto.features.playersForm.navigateToPlayersScreen
import br.com.alexf.futzinmaroto.features.playersForm.playersForm
import br.com.alexf.futzinmaroto.features.randomteams.navigateToRandomTeams
import br.com.alexf.futzinmaroto.features.randomteams.randomTeams

@Composable
fun FutzinMarotoNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = drawTeamsRoute
    ) {
        playersForm(onNavigateToDrawScreen = {
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
        randomTeams()
        gameScreen()
    }
}
