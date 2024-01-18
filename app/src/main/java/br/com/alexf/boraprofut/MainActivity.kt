package br.com.alexf.boraprofut

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.com.alexf.boraprofut.features.game_screen.GameScreen
import br.com.alexf.boraprofut.features.game_screen.GameScreenViewModel
import br.com.alexf.boraprofut.ui.theme.BoraProFutTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel: GameScreenViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BoraProFutTheme {
                GameScreen(viewModel)
            }
        }
    }
}