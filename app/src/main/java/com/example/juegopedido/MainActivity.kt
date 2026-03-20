package com.example.juegopedido

import com.example.juegopedido.features.PantallaResultados.PantallaResultados


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
// Asegúrate de que estas rutas sean correctas según tus carpetas
import com.example.juegopedido.features.MemoryCards.JuegoMemoriaScreen
import com.example.juegopedido.features.MemoryCards.JuegoViewModel
import com.example.juegopedido.features.PantallaInicio.PantallaInicio
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.CardDefaults


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Aplicamos el tema para que no de errores de estilo
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val miViewModel: JuegoViewModel = viewModel()
                    var pantallaActual by remember { mutableStateOf("inicio") }

                    when (pantallaActual) {
                        "inicio" -> {
                            PantallaInicio(onEmpezarJuego = { nombre ->
                                miViewModel.nombreUsuario = nombre
                                miViewModel.iniciarJuego()
                                pantallaActual = "juego"
                            })
                        }
                        "juego" -> {
                            JuegoMemoriaScreen(
                                miViewModel = miViewModel,
                                onIrAResultados = { pantallaActual = "resultados" }
                            )
                        }
                        "resultados" -> {
                            // Si esta función está en otro archivo, asegúrate de importarla
                            PantallaResultados(
                                nombre = miViewModel.nombreUsuario,
                                tiempo = miViewModel.tiempoFinal,
                                onReiniciar = { pantallaActual = "inicio" }
                            )
                        }
                    }
                }
            }
        }
    }
}
