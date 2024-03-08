package br.com.alexf.boraprofut

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import br.com.alexf.boraprofut.navigation.BoraProFutNavHost
import br.com.alexf.boraprofut.ui.theme.BoraProFutTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BoraProFutTheme {
                val navController = rememberNavController()
                Surface(color = MaterialTheme.colorScheme.background) {
                    BoraProFutNavHost(navController)
                }
            }
        }
    }

}

