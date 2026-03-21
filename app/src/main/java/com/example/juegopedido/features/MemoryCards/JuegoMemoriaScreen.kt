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
import androidx.compose.ui.graphics.Brush

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.*

import kotlinx.coroutines.flow.collectLatest
import com.example.juegopedido.R
import androidx.compose.ui.text.style.TextAlign

@Composable
fun JuegoMemoriaScreen(
    miViewModel: JuegoViewModel,
    onIrAResultados: () -> Unit
) {
    val cartas by miViewModel.cartas.collectAsState()
    val parejas by miViewModel.parejasEncontradas.collectAsState()

    // Colores Pokémon
    val pokeAzul = Color(0xFF3761A8)
    val pokeAmarillo = Color(0xFFFFCC00)
    val pokeRojo = Color(0xFFCC0000)

    LaunchedEffect(Unit) {
        miViewModel.eventos.collectLatest { evento ->
            if (evento is JuegoViewModel.EventoJuego.JuegoCompletado) {
                onIrAResultados()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFFFFFFF), Color(0xFFFFF9C4))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Usamos Spacers con peso para forzar el centrado real
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Entrenador: ${miViewModel.nombreUsuario}",
                fontWeight = FontWeight.Bold,
                color = pokeRojo,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Poke-Memory",
                fontSize = 36.sp,
                fontWeight = FontWeight.Black,
                color = pokeAzul,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Panel de puntuación
            Surface(
                tonalElevation = 6.dp,
                shape = RoundedCornerShape(20.dp),
                color = Color.White,
                border = androidx.compose.foundation.BorderStroke(2.dp, pokeAzul),
                modifier = Modifier.fillMaxWidth(0.9f) // Un poco más estrecho para centrar mejor visualmente
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("PAREJAS", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                        Text("$parejas / 8", fontWeight = FontWeight.ExtraBold, fontSize = 24.sp, color = pokeAzul)
                    }
                    Button(
                        onClick = { miViewModel.prepararJuego() },
                        colors = ButtonDefaults.buttonColors(containerColor = pokeRojo),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("REINICIAR", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Tablero centrado
            // Limitamos el ancho del grid para que no se pegue a los bordes y se vea más centrado
            Box(modifier = Modifier.fillMaxWidth(0.95f)) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.wrapContentHeight(),
                    userScrollEnabled = false // Desactivamos scroll para que el Column maneje el centrado
                ) {
                    itemsIndexed(cartas) { indice, carta ->
                        CartaItemView(carta = carta, pokeAzul = pokeAzul, pokeAmarillo = pokeAmarillo) {
                            miViewModel.seleccionarCarta(indice)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1.2f)) // Un poco más de peso abajo para compensar visualmente el cabezal
        }
    }
}

@Composable
fun CartaItemView(carta: CartaMemoria, pokeAzul: Color, pokeAmarillo: Color, alClick: () -> Unit) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (carta.estaVolteada || carta.estaEmparejada) Color.White
                else pokeAmarillo
            )
            .clickable { if (!carta.estaVolteada && !carta.estaEmparejada) alClick() },
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
                color = pokeAzul,
                fontSize = 28.sp,
                fontWeight = FontWeight.Black
            )
        }
    }
}
