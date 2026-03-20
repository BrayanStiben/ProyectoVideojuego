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

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape


@Composable
fun JuegoMemoriaScreen(miViewModel: JuegoViewModel = viewModel()) {
    val cartas by miViewModel.cartas.collectAsState()
    val parejas by miViewModel.parejasEncontradas.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var esErrorSnackbar by remember { mutableStateOf(false) }

    // Escuchar eventos para el Snackbar
    LaunchedEffect(Unit) {
        miViewModel.eventos.collectLatest { evento ->
            when (evento) {
                is JuegoViewModel.EventoJuego.MostrarSnackbar -> {
                    esErrorSnackbar = evento.esError
                    snackbarHostState.showSnackbar(evento.mensaje)
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    containerColor = if (esErrorSnackbar) Color(0xFFD32F2F) else Color(0xFF388E3C),
                    contentColor = Color.White,
                    snackbarData = data
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- CABECERA CON ICONO Y TÍTULO ---
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 20.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icono),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFFCC00)) // Fondo amarillo para el icono
                        .padding(6.dp)
                )

                Spacer(modifier = Modifier.width(15.dp))

                Text(
                    text = "Poke-Memory",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF3761A8)
                )
            }

            // --- PANEL DE CONTROL (SCORE Y RESET) ---
            Surface(
                tonalElevation = 4.dp,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFFF5F5F5)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Progreso", fontSize = 14.sp, color = Color.Gray)
                        Text(
                            text = "$parejas de 8 parejas",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }

                    Button(
                        onClick = { miViewModel.prepararJuego() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFCC00)),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                    ) {
                        Text("Reiniciar", color = Color(0xFF3761A8), fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- TABLERO ---
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.weight(1f)
            ) {
                itemsIndexed(cartas) { indice, carta ->
                    CartaItemView(carta = carta) {
                        miViewModel.seleccionarCarta(indice)
                    }
                }
            }

            // --- ESTADO DE VICTORIA ---
            if (parejas == 8) {
                Text(
                    text = "¡ERES UN MAESTRO! 🏆",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF388E3C),
                    modifier = Modifier.padding(vertical = 20.dp)
                )
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
                modifier = Modifier.fillMaxSize().padding(10.dp)
            )
        } else {
            Text(
                text = "?",
                color = Color(0xFF3761A8),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}