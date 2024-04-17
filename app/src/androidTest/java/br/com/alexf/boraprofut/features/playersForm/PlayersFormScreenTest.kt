package br.com.alexf.boraprofut.features.playersForm

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import br.com.alexf.boraprofut.MainActivity
import br.com.alexf.boraprofut.R
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class PlayersFormScreenTest {

    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    @Test
    fun shouldDisplayTitleAndPlayersTextFieldWhenStartsScreen() {
        rule.activity.setContent {
            PlayersFormScreen(
                uiState = PlayersUiState(),
                onClearPlayers = {},
                onSavePlayers = {}
            )
        }
        rule
            .onNodeWithText(rule.activity.getString(R.string.register_of_players))
            .assertIsDisplayed()
        rule.onNodeWithText(rule.activity.getString(R.string.players_list))
            .assertIsDisplayed()
    }

    @Test
    fun shouldDisplayGoalKeeperInfoIcon_WhenStartsScreen() {
        rule.activity.setContent {
            PlayersFormScreen(
                uiState = PlayersUiState(),
                onClearPlayers = {},
                onSavePlayers = {}
            )
        }
        rule.onNode(
            hasContentDescription(rule.activity.getString(R.string.show_add_goal_keeper_tip))
                    and
                    hasClickAction()
        ).assertIsDisplayed()
    }

    @Test
    fun shouldDisplayGoalKeeperAlertDialog() {
        rule.activity.setContent {
            PlayersFormScreen(
                uiState = PlayersUiState(
                    isGoalKeeperToolTipVisible = flowOf(true)
                ),
                onClearPlayers = {},
                onSavePlayers = {}
            )
        }
        rule.onNodeWithTag(GOAL_KEEPER_DIALOG)
            .assertIsDisplayed()
        rule.onNodeWithText(rule.activity.getString(R.string.add_goal_keeper))
            .assertIsDisplayed()
        rule.onNodeWithText(rule.activity.getString(R.string.mark_player_as_goal_keeper_message))
            .assertIsDisplayed()
        rule.onNode(
            hasText(rule.activity.getString(R.string.got_it))
                    and
                    hasClickAction()
        ).assertIsDisplayed()
    }

    @Test
    fun shouldNotDisplayGoalKeeperAlertDialog() {
        rule.activity.setContent {
            PlayersFormScreen(
                uiState = PlayersUiState(
                    isGoalKeeperToolTipVisible = flowOf(false)
                ),
                onClearPlayers = {},
                onSavePlayers = {}
            )
        }
        rule.onNodeWithTag(GOAL_KEEPER_DIALOG)
            .assertIsNotDisplayed()
    }

    @Test
    fun shouldDisplayPlayersCounterWhenInsertAtLeastOnePlayer() {
        rule.activity.setContent {
            PlayersFormScreen(
                uiState = PlayersUiState(players = "player", 1),
                onClearPlayers = {},
                onSavePlayers = {}
            )
        }
        rule.onNodeWithText(rule.activity.getString(R.string.register_of_players))
            .assertIsDisplayed()
        rule.onNodeWithText(rule.activity.getString(R.string.players_list))
            .assertIsDisplayed()
        rule.onNodeWithText(rule.activity.getString(R.string.players_registered, 1))
            .assertIsDisplayed()
    }

    @Test
    fun shouldDisplayClearButtonWhenInsertSomePlayersInTextField() {
        rule.activity.setContent {
            PlayersFormScreen(
                uiState = PlayersUiState(
                    players = "player"
                ),
                onClearPlayers = {},
                onSavePlayers = {}
            )
        }
        rule.onNodeWithText(rule.activity.getString(R.string.register_of_players))
            .assertIsDisplayed()
        rule.onNodeWithText(rule.activity.getString(R.string.clear))
            .assertHasClickAction()
            .assertIsDisplayed()
    }

    @Test
    fun shouldDisplaySaveButtonWhenInsertAtLeastFourPlayersInTextField() {
        rule.activity.setContent {
            PlayersFormScreen(
                uiState = PlayersUiState(
                    players = "player 1\nplayer 2\nplayer 3\nplayer 4\n",
                    amountPlayers = 4,
                ),
                onClearPlayers = {},
                onSavePlayers = {}
            )
        }
        rule.onNodeWithText(rule.activity.getString(R.string.register_of_players))
            .assertIsDisplayed()
        rule.onNodeWithText(rule.activity.getString(R.string.clear))
            .assertHasClickAction()
            .assertIsDisplayed()
        rule.onNodeWithText(rule.activity.getString(R.string.players_registered, 4))
            .assertIsDisplayed()
        rule.onNodeWithText(rule.activity.getString(R.string.save))
            .assertHasClickAction()
            .assertIsDisplayed()
    }

}