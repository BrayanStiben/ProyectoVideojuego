package com.example.juegopedido.features.MemoryCards
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
 import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

 import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.lifecycle.viewmodel.compose.viewModel
import android.media.MediaPlayer
import androidx.compose.material3.*

import kotlinx.coroutines.flow.collectLatest
import com.example.juegopedido.R

import androidx.compose.foundation.shape.CircleShape



@Composable
fun JuegoMemoriaScreen(
    miViewModel: JuegoViewModel,
    onIrAResultados: () -> Unit // <--- Este es el parámetro que pedía el MainActivity
) {
    val cartas by miViewModel.cartas.collectAsState()
    val parejas by miViewModel.parejasEncontradas.collectAsState()

    // Escuchamos cuando el ViewModel avisa que el juego terminó
    LaunchedEffect(Unit) {
        miViewModel.eventos.collectLatest { evento ->
            if (evento is JuegoViewModel.EventoJuego.JuegoCompletado) {
                // Al ganar, ejecutamos la navegación a la siguiente pantalla
                onIrAResultados()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Títulos e información del jugador
        Text(
            text = "Entrenador: ${miViewModel.nombreUsuario}",
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = "Poke-Memory",
            fontSize = 34.sp,
            fontWeight = FontWeight.Black,
            color = Color(0xFF3761A8)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Panel de puntuación actual
        Surface(
            tonalElevation = 4.dp,
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFFF5F5F5),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Parejas: $parejas / 8", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Button(
                    onClick = { miViewModel.prepararJuego() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFCC00))
                ) {
                    Text("Reiniciar", color = Color(0xFF3761A8), fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // El Tablero de Juego
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(cartas) { indice, carta ->
                CartaItemView(carta = carta) {
                    miViewModel.seleccionarCarta(indice)
                }
            }
        }
    }
}

@Composable
fun CartaItemView(carta: CartaMemoria, alClick: () -> Unit) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (carta.estaVolteada || carta.estaEmparejada) Color.White
                else Color(0xFFFFCC00)
            )
            .clickable { alClick() },
        contentAlignment = Alignment.Center
    ) {
        if (carta.estaVolteada || carta.estaEmparejada) {
            Image(
                painter = painterResource(id = carta.imagenRes),
                contentDescription = null,
                modifier = Modifier.fillMaxSize().padding(8.dp)
            )
        } else {
            Text(
                text = "?",
                color = Color(0xFF3761A8),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}