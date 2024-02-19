package br.com.alexf.boraprofut.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import br.com.alexf.boraprofut.features.balancedTeams.balancedTeams
import br.com.alexf.boraprofut.features.balancedTeams.navigateToBalancedTeams
import br.com.alexf.boraprofut.features.drawTeams.drawTeams
import br.com.alexf.boraprofut.features.drawTeams.drawTeamsRoute
import br.com.alexf.boraprofut.features.drawTeams.navigateToDrawTeams
import br.com.alexf.boraprofut.features.game.navigation.gameScreen
import br.com.alexf.boraprofut.features.playersForm.navigateToPlayersFormScreen
import br.com.alexf.boraprofut.features.playersForm.playersForm
import br.com.alexf.boraprofut.features.playersForm.playersFormRoute
import br.com.alexf.boraprofut.features.randomteams.navigateToRandomTeams
import br.com.alexf.boraprofut.features.randomteams.randomTeams

@Composable
fun BoraProFutNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = drawTeamsRoute
    ) {
        playersForm(onNavigateToDrawScreen = {
            navController.navigateToDrawTeams(navOptions {
                popUpTo(playersFormRoute) {
                    inclusive = true
                }
            })
        })
        drawTeams(
            onNavigateToRandomTeams = {
                navController.navigateToRandomTeams()
            },
            onNavigateToBalancedTeams = {
                navController.navigateToBalancedTeams()
            },
            onNavigateToPlayersFormScreen = {
                navController.navigateToPlayersFormScreen(
                    navOptions {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                )
            }
        )
        balancedTeams()
        randomTeams()
        gameScreen()
    }
}
