package br.com.alexf.boraprofut.features.drawTeams

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import br.com.alexf.boraprofut.MainActivity
import br.com.alexf.boraprofut.R
import br.com.alexf.boraprofut.models.Player
import org.junit.Rule
import org.junit.Test

private const val PLAYERS_PER_TEAM = 4

class DrawTeamsScreenTest {

    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    private val players = listOf(
        Player(name = "goalkeeper", isGoalKeeper = true),
        Player(name = "player 1"),
        Player(name = "player 2"),
        Player(name = "player 3"),
    )

    @Test
    fun shouldDisplayAllComponents() {
        rule.activity.setContent {
            DrawTeamsScreen(
                uiState = DrawTeamsUiState(
                    players = players.toSet(),
                    playersPerTeam = PLAYERS_PER_TEAM,
                    isShowPlayers = true
                ),
                onDrawRandomTeamsClick = {},
                onDrawBalancedTeamsClick = {}
            ) {}
        }
        rule.onNodeWithText(rule.activity.getString(R.string.teams_draw))
            .assertIsDisplayed()
        rule.onNodeWithText(rule.activity.getString(R.string.players_amount_per_team))
            .assertIsDisplayed()
        rule.onNode(
            hasContentDescription(rule.activity.getString(R.string.decreases_players_amount_per_team))
                    and
                    hasClickAction()
        ).assertIsDisplayed()
        rule.onNodeWithText(PLAYERS_PER_TEAM.toString())
            .assertIsDisplayed()
        rule.onNode(
            hasContentDescription(rule.activity.getString(R.string.increases_players_amount_per_team))
                    and
                    hasClickAction()
        ).assertIsDisplayed()
        rule.onNodeWithText(rule.activity.getString(R.string.random))
            .assertIsDisplayed()
        rule.onNodeWithContentDescription(rule.activity.getString(R.string.random))
            .assertIsDisplayed()
        rule.onNodeWithText(rule.activity.getString(R.string.balanced))
            .assertIsDisplayed()
        rule.onNodeWithContentDescription(rule.activity.getString(R.string.balanced))
            .assertIsDisplayed()
        rule.onNodeWithContentDescription(
            rule.activity.getString(R.string.icon_of_display_players_button)
        )
            .assertIsDisplayed()
            .assertHasClickAction()
        rule.onNodeWithText("${rule.activity.getString(R.string.hide_players)} (${players.size})")
            .assertIsDisplayed()
            .assertHasClickAction()
        rule.onNodeWithText(rule.activity.getString(R.string.players, players.size))
            .assertIsDisplayed()
        rule.onNodeWithTag(EDIT_PLAYERS_BUTTON)
            .assertHasClickAction()
            .assertIsDisplayed()
        rule.onNodeWithTag(PLAYERS_LIST)
            .assertIsDisplayed()
        rule.onAllNodesWithTag(PLAYERS_LIST_ITEM)
            .assertCountEquals(4)
    }

    @Test
    fun shouldNotDisplayPlayersList_WhenIsShowPlayersIsFalse() {
        rule.activity.setContent {
            DrawTeamsScreen(
                uiState = DrawTeamsUiState(
                    players = players.toSet(),
                    playersPerTeam = PLAYERS_PER_TEAM,
                    isShowPlayers = false
                ),
                onDrawRandomTeamsClick = {},
                onDrawBalancedTeamsClick = {}
            ) {}
        }
        rule.onNodeWithContentDescription(
            rule.activity.getString(R.string.icon_of_hide_players_button)
        ).assertIsDisplayed()
        rule.onNodeWithText("${rule.activity.getString(R.string.show_players)} (${players.size})")
            .assertIsDisplayed()
        rule.onNodeWithText(rule.activity.getString(R.string.players, players.size))
            .assertIsNotDisplayed()
        rule.onNodeWithTag(EDIT_PLAYERS_BUTTON)
            .assertIsNotDisplayed()
        rule.onNodeWithTag(PLAYERS_LIST)
            .assertIsNotDisplayed()
    }


}