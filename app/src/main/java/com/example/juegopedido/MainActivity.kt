package com.example.juegopedido

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
 import androidx.compose.foundation.layout.fillMaxSize
 import androidx.compose.material3.MaterialTheme
 import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
 import com.example.juegopedido.features.MemoryCards.JuegoMemoriaScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Aplicamos un tema básico de Material3
            MaterialTheme {
                // Un contenedor que usa el color de fondo del tema
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // AQUÍ LLAMAMOS A TU PANTALLA
                    JuegoMemoriaScreen()
                }
            }
        }
    }
}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
