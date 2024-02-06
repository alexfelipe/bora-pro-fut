package br.com.alexf.boraprofut.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import br.com.alexf.boraprofut.features.balancedTeams.balancedTeams
import br.com.alexf.boraprofut.features.balancedTeams.navigateToBalancedTeams
import br.com.alexf.boraprofut.features.drawTeams.drawTeams
import br.com.alexf.boraprofut.features.drawTeams.drawTeamsRoute
import br.com.alexf.boraprofut.features.drawTeams.navigateToDrawTeams
import br.com.alexf.boraprofut.features.game.navigation.gameScreen
import br.com.alexf.boraprofut.features.playersForm.navigateToPlayersScreen
import br.com.alexf.boraprofut.features.playersForm.playersForm
import br.com.alexf.boraprofut.features.randomteams.navigateToRandomTeams
import br.com.alexf.boraprofut.features.randomteams.randomTeams

@Composable
fun BoraProFutNavHost(navController: NavHostController) {
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
