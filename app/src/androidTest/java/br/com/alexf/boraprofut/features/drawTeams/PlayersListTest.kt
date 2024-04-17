package br.com.alexf.boraprofut.features.drawTeams

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import br.com.alexf.boraprofut.models.Player
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random

class PlayersListTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun shouldDisplayPlayersList() {
        val players = listOf(
            Player("Goalkeeper", isGoalKeeper = true),
            Player("Player", level = Random.nextInt(1, 10))
        )
        rule.setContent {
            PlayersList(
                uiState = DrawTeamsUiState(
                    players = players.toSet()
                )
            )
        }
        rule.onNodeWithText("${players[0].name} (G)").assertIsDisplayed()
        rule.onNodeWithText(players[1].name).assertIsDisplayed()
        rule.onAllNodesWithTag(PLAYERS_LIST_ITEM).assertCountEquals(2)
    }

    @Test
    fun shouldDisplayPlayerLevelMenu_WhenPlayerIsNotGoalkeeper() {
        rule.setContent {
            PlayersList(
                uiState = DrawTeamsUiState(
                    players = setOf(
                        Player("Player")
                    )
                )
            )
        }
        rule.onNodeWithTag(PLAYER_LEVEL_MENU).assertIsDisplayed()
    }

    @Test
    fun shouldNotDisplayPlayerLevelMenu_WhenPlayerIsGoalkeeper() {
        rule.setContent {
            PlayersList(
                uiState = DrawTeamsUiState(
                    players = setOf(
                        Player("Goalkeeper", isGoalKeeper = true)
                    )
                )
            )
        }
        rule.onNodeWithTag(PLAYER_LEVEL_MENU).assertIsNotDisplayed()
    }
}