package br.com.alexf.boraprofut.features

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import br.com.alexf.boraprofut.features.playersForm.PlayersFormScreen
import br.com.alexf.boraprofut.features.playersForm.PlayersUiState
import org.junit.Rule
import org.junit.Test

class PlayersFormScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldDisplayTitleAndPlayersTextFieldWhenStartsScreen() {
        composeTestRule.setContent {
            PlayersFormScreen(
                uiState = PlayersUiState(),
                onClearPlayers = {},
                onSavePlayers = {}
            )
        }
        composeTestRule.onNodeWithText("Cadastro de jogadores")
            .isDisplayed()
        composeTestRule.onNodeWithText("Lista de jogadores")
            .isDisplayed()
    }

    @Test
    fun shouldDisplayPlayersCounterWhenInsertAtLeastOnePlayer(){
        composeTestRule.setContent {
            PlayersFormScreen(
                uiState = PlayersUiState("alex", 1),
                onClearPlayers = {},
                onSavePlayers = {}
            )
        }
        composeTestRule.onNodeWithText("Cadastro de jogadores")
            .isDisplayed()
        composeTestRule.onNodeWithText("Lista de jogadores")
            .isDisplayed()
        composeTestRule.onNodeWithText("Jogadores: 1")
            .isDisplayed()
    }

    @Test
    fun shouldDisplayClearButtonWhenInsertSomePlayersInTextField(){
        composeTestRule.setContent {
            PlayersFormScreen(
                uiState = PlayersUiState(
                    players = "alex"
                ),
                onClearPlayers = {},
                onSavePlayers = {}
            )
        }
        composeTestRule.onNodeWithText("Cadastro de jogadores")
            .isDisplayed()
        composeTestRule.onNodeWithText("Limpar")
            .assertHasClickAction()
            .isDisplayed()
    }

    @Test
    fun shouldDisplaySaveButtonWhenInsertAtLeastFourPlayersInTextField() {
        composeTestRule.setContent {
            PlayersFormScreen(
                uiState = PlayersUiState(
                    players = "alex\nthaylan\ndaniel\nmaria\n",
                    amountPlayers = 4,
                ),
                onClearPlayers = {},
                onSavePlayers = {}
            )
        }
        composeTestRule.onNodeWithText("Cadastro de jogadores")
            .isDisplayed()
        composeTestRule.onNodeWithText("Limpar")
            .assertHasClickAction()
            .isDisplayed()
        composeTestRule.onNodeWithText("Jogadores: 4")
            .isDisplayed()
        composeTestRule.onNodeWithText("Salvar")
            .assertHasClickAction()
            .isDisplayed()
    }

}