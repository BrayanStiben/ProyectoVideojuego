package com.example.juegopedido.features.PantallaInicio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PantallaInicio(
    viewModel: InicioViewModel = viewModel(),
    onEmpezarJuego: (String) -> Unit
) {
    val nombre = viewModel.nombreInput
    val esValido = viewModel.esNombreValido

    val pokeAzul = Color(0xFF3761A8)
    val pokeAmarillo = Color(0xFFFFCC00)
    val pokeRojo = Color(0xFFCC0000)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFFFFFFF), Color(0xFFE3F2FD))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Poke-Memory",
                fontSize = 40.sp,
                fontWeight = FontWeight.ExtraBold,
                color = pokeAzul,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "¡Hazte con todas las parejas!",
                fontSize = 18.sp,
                color = pokeRojo,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "¡Hola! Encuentra las 8 parejas de Pokémon. Escribe tu nombre para comenzar.",
                textAlign = TextAlign.Center,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = nombre,
                onValueChange = { viewModel.onNombreCambiado(it) },
                label = { Text("Nombre del Entrenador") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = pokeAzul,
                    focusedLabelColor = pokeAzul,
                    cursorColor = pokeAzul
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { onEmpezarJuego(nombre) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                enabled = esValido,
                colors = ButtonDefaults.buttonColors(
                    containerColor = pokeAmarillo,
                    contentColor = pokeAzul,
                    disabledContainerColor = Color.LightGray
                ),
                elevation = ButtonDefaults.buttonElevation(8.dp)
            ) {
                Text(
                    "¡COMENZAR AVENTURA!",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
