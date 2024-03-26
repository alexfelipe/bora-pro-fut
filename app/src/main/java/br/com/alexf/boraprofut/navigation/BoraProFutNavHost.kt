package br.com.alexf.boraprofut.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PeopleAlt
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import br.com.alexf.boraprofut.features.balancedTeams.balancedTeams
import br.com.alexf.boraprofut.features.balancedTeams.navigateToBalancedTeams
import br.com.alexf.boraprofut.features.drawTeams.drawTeams
import br.com.alexf.boraprofut.features.drawTeams.drawTeamsRoute
import br.com.alexf.boraprofut.features.game.navigation.gameScreen
import br.com.alexf.boraprofut.features.playersForm.PLAYERS_FORM_ROUTE
import br.com.alexf.boraprofut.features.playersForm.navigateToPlayersFormScreen
import br.com.alexf.boraprofut.features.playersForm.playersForm
import br.com.alexf.boraprofut.features.randomteams.navigateToRandomTeams
import br.com.alexf.boraprofut.features.randomteams.randomTeams
import br.com.alexf.boraprofut.features.timer.timer
import br.com.alexf.boraprofut.features.timer.timerRoute

@Composable
fun BoraProFutNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = homeRoute
    ) {
        playersForm(onNavigateToDrawScreen = {
            navController.navigateToHomeGraph(navOptions {
                popUpTo(PLAYERS_FORM_ROUTE) {
                    inclusive = true
                }
            })
        })
        balancedTeams()
        randomTeams()
        home(
            onNavigateToRandomTeams = {
                navController.navigateToRandomTeams()
            }, onNavigateToBalancedTeams = {
                navController.navigateToBalancedTeams()
            }, onNavigateToPlayersForm = {
                navController.navigateToPlayersFormScreen(
                    navOptions {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                )

            })
        gameScreen()
    }
}

sealed class HomeNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
) {
    data object DrawTeams : HomeNavItem(
        label = "Sorteio",
        icon = Icons.Outlined.PeopleAlt,
        route = drawTeamsRoute
    )

    data object Timer : HomeNavItem(
        label = "Cronômetro",
        icon = Icons.Outlined.Timer,
        route = timerRoute
    )
}

const val homeRoute = "home"

private fun NavGraphBuilder.home(
    onNavigateToRandomTeams: () -> Unit,
    onNavigateToBalancedTeams: () -> Unit,
    onNavigateToPlayersForm: () -> Unit,
) {
    composable(homeRoute) {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination?.route
        val items = remember {
            listOf(
                HomeNavItem.DrawTeams,
                HomeNavItem.Timer
            )
        }
        Column(Modifier.fillMaxSize()) {
            NavHost(
                navController = navController,
                startDestination = drawTeamsRoute,
                Modifier.weight(1f)
            ) {
                drawTeams(
                    onNavigateToRandomTeams = onNavigateToRandomTeams ,
                    onNavigateToBalancedTeams = onNavigateToBalancedTeams,
                    onNavigateToPlayersForm = onNavigateToPlayersForm
                )
                timer()
            }
            BottomAppBar(
                actions = {
                    items.forEach { item ->
                        NavigationBarItem(
                            selected = currentDestination == item.route,
                            onClick = {
                                navController.navigate(item.route,
                                    navOptions {
                                        launchSingleTop = true
                                        popUpTo(navController.graph.startDestinationId)
                                    }
                                )
                            },
                            icon = {
                                Icon(item.icon, contentDescription = null)
                            },
                            label = {
                                Text(text = item.label)
                            }
                        )
                    }
                }
            )
        }
    }
}

fun NavController.navigateToHomeGraph(navOptions: NavOptions? = null){
    navigate(homeRoute, navOptions)
}