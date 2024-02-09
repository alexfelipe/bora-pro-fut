package br.com.alexf.boraprofut

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import br.com.alexf.boraprofut.navigation.BoraProFutNavHost
import br.com.alexf.boraprofut.ui.theme.BoraProFutTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BoraProFutTheme {

                var remember by remember { mutableStateOf("") }

                OutlinedTextField(
                    value = remember,
                    onValueChange = {
                        remember = it
                    }
                )
            }
        }
    }

}

