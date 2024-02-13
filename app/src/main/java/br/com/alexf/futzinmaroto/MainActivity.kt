package br.com.alexf.futzinmaroto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import br.com.alexf.futzinmaroto.navigation.FutzinMarotoNavHost
import br.com.alexf.futzinmaroto.ui.theme.FutzinMarotoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FutzinMarotoTheme {
                val navController = rememberNavController()
                Surface(color = MaterialTheme.colorScheme.background) {
                    FutzinMarotoNavHost(navController)
                }
            }
        }
    }

}

